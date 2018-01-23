package com.navteq.vps.gjv.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.vecmath.Point2f;
import javax.vecmath.Vector2f;

import com.navteq.vps.gjv.latcreation3dgj.JunctionComponentsHandler;
import com.navteq.vps.gjv.latcreation3dgj.data.CurveJunctionComponent;
import com.navteq.vps.gjv.latcreation3dgj.data.CurveJunctionComponent.CurveType;
import com.navteq.vps.gjv.latcreation3dgj.data.ForkJunctionComponent;
import com.navteq.vps.gjv.latcreation3dgj.data.JunctionComponent;
import com.navteq.vps.gjv.latcreation3dgj.data.LatEntry3DGJ;
import com.navteq.vps.gjv.utils.junctionprotos.CommonProtos.Vector3f;
import com.navteq.vps.gjv.utils.junctionprotos.JunctionComponent3D;
import com.navteq.vps.gjv.utils.junctionprotos.JunctionPieceProtos.JunctionPiece.Lane;

public class GenericJunctionSVG
{
    /**
     * Creates 2D generic junction representation as SVG file
     * 
     * @param latEntry
     * @param outputSvgFile
     * @param junctionComponentsHandler
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static void save(LatEntry3DGJ latEntry, File outputSvgFile, JunctionComponentsHandler junctionComponentsHandler) throws FileNotFoundException,
            UnsupportedEncodingException
    {
        StringBuilder sb = new StringBuilder();

        writeSVGStart(sb);

        // *** DP1 Incoming ***
        ComponentPosition componentPosition = new ComponentPosition(new Vector2f(0, 1), new Point2f(0, 0));
        componentPosition = addComponent(latEntry.getDp1Incoming(), componentPosition, junctionComponentsHandler, sb);

        // *** DP1 Fork ***
        ForkJunctionComponent dp1Fork = (ForkJunctionComponent) junctionComponentsHandler.getJunctionComponentByName(latEntry.getDp1Fork());
        addComponentToSvg(dp1Fork, componentPosition, getComponentStartPosition(dp1Fork), sb);
        ComponentPosition rightComponentPosition = computeNextPosition(componentPosition, getComponentStartPosition(dp1Fork), new ComponentPosition(
                new Vector2f(dp1Fork.getRightEndDirection().getX(), dp1Fork.getRightEndDirection().getY()), new Point2f(dp1Fork.getRightEndPoint().getX(),
                        dp1Fork.getRightEndPoint().getY())));

        ComponentPosition leftComponentPosition = computeNextPosition(componentPosition, getComponentStartPosition(dp1Fork), new ComponentPosition(
                new Vector2f(dp1Fork.getLeftEndDirection().getX(), dp1Fork.getLeftEndDirection().getY()), new Point2f(dp1Fork.getLeftEndPoint().getX(), dp1Fork
                        .getLeftEndPoint().getY())));

        // *** Right path ***
        JunctionComponent dp1Outgoing2 = junctionComponentsHandler.getJunctionComponentByName(latEntry.getDp1Outgoing2());
        addComponentToSvg(dp1Outgoing2, rightComponentPosition, getComponentStartPosition(dp1Outgoing2), sb);
        rightComponentPosition = computeNextPosition(rightComponentPosition, getComponentStartPosition(dp1Outgoing2), getComponentEndPosition(dp1Outgoing2));

        boolean rightPathIsCloverleaf = dp1Outgoing2 instanceof CurveJunctionComponent
                && ((CurveJunctionComponent) dp1Outgoing2).getCurveType() != CurveType.HORIZONTAL;

        rightComponentPosition = addComponent(latEntry.getDp1Outgoing2Extender1(), rightComponentPosition, junctionComponentsHandler, sb);
        rightComponentPosition = addComponent(latEntry.getDp1Outgoing2Extender2(), rightComponentPosition, junctionComponentsHandler, sb);

        // *** Left path ***
        JunctionComponent dp1Outgoing1 = junctionComponentsHandler.getJunctionComponentByName(latEntry.getDp1Outgoing1());
        addComponentToSvg(dp1Outgoing1, leftComponentPosition, getComponentStartPosition(dp1Outgoing1), sb);
        leftComponentPosition = computeNextPosition(leftComponentPosition, getComponentStartPosition(dp1Outgoing1), getComponentEndPosition(dp1Outgoing1));

        boolean leftPathIsCloverleaf = dp1Outgoing1 instanceof CurveJunctionComponent
                && ((CurveJunctionComponent) dp1Outgoing1).getCurveType() != CurveType.HORIZONTAL;

        leftComponentPosition = addComponent(latEntry.getDp1Outgoing1Extender1(), leftComponentPosition, junctionComponentsHandler, sb);
        leftComponentPosition = addComponent(latEntry.getDp1Outgoing1Extender2(), leftComponentPosition, junctionComponentsHandler, sb);

        // *** cloverleaf ***
        if ((leftPathIsCloverleaf || rightPathIsCloverleaf) && componentIsListed(latEntry.getDp1Merge()))
        {
            ForkJunctionComponent dp1Merge = (ForkJunctionComponent) junctionComponentsHandler.getJunctionComponentByName(latEntry.getDp1Merge());

            ComponentPosition mergeOutgoing = null;
            ComponentPosition forkConnectingPosition = null;
            ComponentPosition mergeIncomingConnectingPosition = null;

            if (rightPathIsCloverleaf)
            {
                mergeOutgoing = rightComponentPosition;

                // connecting to fork left
                forkConnectingPosition = new ComponentPosition(new Vector2f(dp1Merge.getLeftEndDirection().getX() * -1, dp1Merge.getLeftEndDirection().getY()
                        * -1), new Point2f(dp1Merge.getLeftEndPoint().getX(), dp1Merge.getLeftEndPoint().getY()));
                mergeIncomingConnectingPosition = new ComponentPosition(new Vector2f(dp1Merge.getRightEndDirection().getX() * -1, dp1Merge
                        .getRightEndDirection().getY() * -1), new Point2f(dp1Merge.getRightEndPoint().getX(), dp1Merge.getRightEndPoint().getY()));
            }
            else
            {
                mergeOutgoing = leftComponentPosition;

                // connecting to fork right
                mergeIncomingConnectingPosition = new ComponentPosition(new Vector2f(dp1Merge.getLeftEndDirection().getX() * -1, dp1Merge.getLeftEndDirection()
                        .getY() * -1), new Point2f(dp1Merge.getLeftEndPoint().getX(), dp1Merge.getLeftEndPoint().getY()));
                forkConnectingPosition = new ComponentPosition(new Vector2f(dp1Merge.getRightEndDirection().getX() * -1, dp1Merge.getRightEndDirection().getY()
                        * -1), new Point2f(dp1Merge.getRightEndPoint().getX(), dp1Merge.getRightEndPoint().getY()));
            }

            // *** merge fork ***
            addComponentToSvg(dp1Merge, mergeOutgoing, forkConnectingPosition, sb);

            // *** merge incoming ***
            // set opposite direction
            mergeIncomingConnectingPosition = new ComponentPosition(new Vector2f(mergeIncomingConnectingPosition.getDirection().x * -1,
                    mergeIncomingConnectingPosition.getDirection().y * -1), mergeIncomingConnectingPosition.getPosition());
            ComponentPosition mergeIncomingPosition = computeNextPosition(mergeOutgoing, forkConnectingPosition, mergeIncomingConnectingPosition);

            mergeIncomingPosition = addComponent(latEntry.getDp1MergeIncoming(), mergeIncomingPosition, junctionComponentsHandler, sb);
            mergeIncomingPosition = addComponent(latEntry.getDp1MergeIncomingExtender1(), mergeIncomingPosition, junctionComponentsHandler, sb);
            mergeIncomingPosition = addComponent(latEntry.getDp1MergeIncomingExtender2(), mergeIncomingPosition, junctionComponentsHandler, sb);

            // *** merge outgoing ***
            mergeOutgoing = computeNextPosition(mergeOutgoing, forkConnectingPosition, new ComponentPosition(new Vector2f(dp1Merge.getStartDirectionVector()
                    .getX() * -1, dp1Merge.getStartDirectionVector().getY() * -1),
                    new Point2f(dp1Merge.getStartPoint().getX(), dp1Merge.getStartPoint().getY())));

            mergeOutgoing = addComponent(latEntry.getDp1MergeOutgoing(), mergeOutgoing, junctionComponentsHandler, sb);
            mergeOutgoing = addComponent(latEntry.getDp1MergeOutgoingExtender1(), mergeOutgoing, junctionComponentsHandler, sb);
            mergeOutgoing = addComponent(latEntry.getDp1MergeOutgoingExtender2(), mergeOutgoing, junctionComponentsHandler, sb);

            if (rightPathIsCloverleaf)
            {
                rightComponentPosition = mergeOutgoing;
            }
            else
            {
                leftComponentPosition = mergeOutgoing;
            }
        }

        // *** 2DP ***
        if (latEntry.getDp2Incoming() != null && !latEntry.getDp2Incoming().isEmpty())
        {
            // is there better way to find out DP2 side?
            boolean dp2IsOnRight = latEntry.getJunctionTemplate().substring(7, 8).equals("R");
            ComponentPosition dp2ComponentPosition = null;

            if (dp2IsOnRight)
            {
                dp2ComponentPosition = rightComponentPosition;
            }
            else
            {
                dp2ComponentPosition = leftComponentPosition;
            }

            dp2ComponentPosition = addComponent(latEntry.getDp2Incoming(), dp2ComponentPosition, junctionComponentsHandler, sb);

            ForkJunctionComponent dp2Fork = (ForkJunctionComponent) junctionComponentsHandler.getJunctionComponentByName(latEntry.getDp2Fork());
            addComponentToSvg(dp2Fork, dp2ComponentPosition, getComponentStartPosition(dp2Fork), sb);

            ComponentPosition dp2RightComponentPosition = computeNextPosition(dp2ComponentPosition, getComponentStartPosition(dp2Fork), new ComponentPosition(
                    new Vector2f(dp2Fork.getRightEndDirection().getX(), dp2Fork.getRightEndDirection().getY()), new Point2f(dp2Fork.getRightEndPoint().getX(),
                            dp2Fork.getRightEndPoint().getY())));

            ComponentPosition dp2LeftComponentPosition = computeNextPosition(dp2ComponentPosition, getComponentStartPosition(dp2Fork), new ComponentPosition(
                    new Vector2f(dp2Fork.getLeftEndDirection().getX(), dp2Fork.getLeftEndDirection().getY()), new Point2f(dp2Fork.getLeftEndPoint().getX(),
                            dp2Fork.getLeftEndPoint().getY())));

            // *** 2DP Right path ***
            dp2RightComponentPosition = addComponent(latEntry.getDp2Outgoing2(), dp2RightComponentPosition, junctionComponentsHandler, sb);
            dp2RightComponentPosition = addComponent(latEntry.getDp2Outgoing2Extender1(), dp2RightComponentPosition, junctionComponentsHandler, sb);
            dp2RightComponentPosition = addComponent(latEntry.getDp2Outgoing2Extender2(), dp2RightComponentPosition, junctionComponentsHandler, sb);

            // *** 2DP Left path ***
            dp2LeftComponentPosition = addComponent(latEntry.getDp2Outgoing1(), dp2LeftComponentPosition, junctionComponentsHandler, sb);
            dp2LeftComponentPosition = addComponent(latEntry.getDp2Outgoing1Extender1(), dp2LeftComponentPosition, junctionComponentsHandler, sb);
            dp2LeftComponentPosition = addComponent(latEntry.getDp2Outgoing1Extender2(), dp2LeftComponentPosition, junctionComponentsHandler, sb);
        }

        writeSVGEnd(sb);

        PrintWriter writer = new PrintWriter(outputSvgFile.getAbsolutePath(), "UTF-8");
        writer.print(sb.toString());
        writer.close();
    }

    /**
     * Adds component to svg if component is present and computes new connection point
     * 
     * @param componentName
     * @param connectingPosition
     * @param junctionComponentsHandler
     * @param sb
     * @return
     */
    private static ComponentPosition addComponent(String componentName, ComponentPosition connectingPosition,
            JunctionComponentsHandler junctionComponentsHandler, StringBuilder sb)
    {
        ComponentPosition newConnectingPosition = null;

        if (componentIsListed(componentName))
        {
            JunctionComponent dp2Outgoing1Extender2 = junctionComponentsHandler.getJunctionComponentByName(componentName);
            addComponentToSvg(dp2Outgoing1Extender2, connectingPosition, getComponentStartPosition(dp2Outgoing1Extender2), sb);
            newConnectingPosition = computeNextPosition(connectingPosition, getComponentStartPosition(dp2Outgoing1Extender2),
                    getComponentEndPosition(dp2Outgoing1Extender2));
        }
        else
        {
            newConnectingPosition = connectingPosition;
        }

        return newConnectingPosition;
    }

    private static boolean componentIsListed(String componentValue)
    {
        return componentValue != null && !componentValue.isEmpty();
    }

    private static ComponentPosition getComponentStartPosition(JunctionComponent component)
    {
        return new ComponentPosition(new Vector2f(component.getStartDirectionVector().getX(), component.getStartDirectionVector().getY()), new Point2f(
                component.getStartPoint().getX(), component.getStartPoint().getY()));
    }

    private static ComponentPosition getComponentEndPosition(JunctionComponent component)
    {
        return new ComponentPosition(new Vector2f(component.getEndDirectionVector().getX(), component.getEndDirectionVector().getY()), new Point2f(component
                .getEndPoint().getX(), component.getEndPoint().getY()));
    }

    private static ComponentPosition computeNextPosition(ComponentPosition componentStartPosition, ComponentPosition componentLocalStartPosition,
            ComponentPosition componentLocalEndPosition)
    {
        Point2f move = new Point2f(componentStartPosition.getPosition());
        move.sub(new Point2f(componentLocalStartPosition.getPosition().getX(), componentLocalStartPosition.getPosition().getY()));

        float angleRad = signedAngle(componentStartPosition.getDirection(), componentLocalStartPosition.getDirection()) * -1;

        Point2f endPoint = new Point2f(componentLocalEndPosition.getPosition().getX(), componentLocalEndPosition.getPosition().getY());
        endPoint.add(move);
        endPoint.sub(componentStartPosition.getPosition());
        Vector2f temp = rotate(angleRad, new Vector2f(endPoint));
        endPoint = new Point2f(temp);
        endPoint.add(componentStartPosition.getPosition());

        Vector2f endDirection = new Vector2f(componentLocalEndPosition.getDirection().getX(), componentLocalEndPosition.getDirection().getY());
        endDirection = rotate(angleRad, endDirection);

        ComponentPosition endPosition = new ComponentPosition(endDirection, endPoint);

        return endPosition;
    }

    private static void addComponentToSvg(JunctionComponent component, ComponentPosition connectToPosition, ComponentPosition connectWhatPosition,
            StringBuilder sb)
    {
        JunctionComponent3D model3D = component.getJunctionComponent3D();

        Point2f move = new Point2f(connectToPosition.getPosition());
        move.sub(new Point2f(connectWhatPosition.getPosition().getX(), connectWhatPosition.getPosition().getY()));

        float angleRad = signedAngle(connectToPosition.getDirection(), connectWhatPosition.getDirection()) * -1;

        float angle = (float) (angleRad / Math.PI * 180);

        sb.append(String.format("<g transform=\"rotate(%s %s %s) translate(%s %s)\">\n", angle, connectToPosition.getPosition().getX(), connectToPosition
                .getPosition().getY(), move.getX(), move.getY()));

        for (JunctionComponent3D.OutlineLine line : model3D.getOutline())
        {
            sb.append(String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" style=\"stroke:rgb(0,0,0);stroke-width:0.3\" />\n", line.getFirstPoint().x,
                    line.getFirstPoint().y, line.getSecondPoint().x, line.getSecondPoint().y));
        }

        for (int i = 0; i < model3D.getLanes().size(); i++)
        {
            Lane lane = model3D.getLanes().get(i);

            for (int j = 1; j < lane.getCurveCount(); j++)
            {
                Vector3f vertex1 = lane.getCurve(j - 1);
                Vector3f vertex2 = lane.getCurve(j);

                sb.append(String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" style=\"stroke:rgb(255,0,0);stroke-width:0.4\" />\n", vertex1.getX(),
                        vertex1.getY(), vertex2.getX(), vertex2.getY()));
            }
        }

        sb.append("</g>\n");
    }

    private static float signedAngle(Vector2f v1, Vector2f v2)
    {
        float perpDot = v1.x * v2.y - v1.y * v2.x;

        return (float) Math.atan2(perpDot, v1.dot(v2));
    }

    private static Vector2f rotate(float angle, Vector2f vector)
    {
        double rx = (vector.getX() * Math.cos(angle)) - (vector.getY() * Math.sin(angle));
        double ry = (vector.getX() * Math.sin(angle)) + (vector.getY() * Math.cos(angle));
        return new Vector2f((float) rx, (float) ry);

    }

    private static void writeSVGStart(StringBuilder sb)
    {
        sb.append("<?xml version=\"1.0\" standalone=\"no\"?>\n");
        sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\"\n");
        sb.append("\txmlns:xlink=\"http://www.w3.org/1999/xlink\"\n");
        sb.append("\tcontentScriptType=\"text/ecmascript\"\n");
        sb.append("\tzoomAndPan=\"magnify\"\n");
        sb.append("\tcontentStyleType=\"text/css\"\n");
        sb.append("\tversion=\"1.1\"\n");
        sb.append("\txml:space=\"preserve\"\n");
        sb.append("\tpreserveAspectRatio=\"xMidYMid meet\"\n");
        sb.append("\twidth=\"1280\"\n");
        sb.append("\theight=\"960\"\n");
        sb.append("\tviewBox=\"-500 -100 1000 650\"\n");
        sb.append("\tx=\"0px\"\n");
        sb.append("\ty=\"0px\">\n");
        sb.append("\t<g transform=\"translate(0, 450) scale(1, -1)\">");
        sb.append("<circle cx=\"" + 0 + "\" cy=\"" + 0 + "\" r=\"2\" fill=\"green\" />\n");
    }

    private static void writeSVGEnd(StringBuilder sb)
    {
        sb.append("</g>\n");
        sb.append("</svg>\n");
    }

    public static class ComponentPosition
    {
        private final Vector2f direction;
        private final Point2f position;

        public Vector2f getDirection()
        {
            return direction;
        }

        public Point2f getPosition()
        {
            return position;
        }

        public ComponentPosition(Vector2f direction, Point2f position)
        {
            this.direction = direction;
            this.position = position;
        }
    }
}
