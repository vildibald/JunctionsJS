package com.navteq.vps.gjv.latcreation3dgj.data;

import com.navteq.vps.gjv.latcreation3dgj.parsingJunctionTemplate.JunctionComponentParser;

public class LatEntry3DGJ
{
    private String junctionTemplate;

    // DP1 INCOMINGS
    private String dp1Incoming;
    private String dp1IncomingExtender;
    private String dp1IncomingExtenderCount;

    // DP1 OUTGOINGS
    private String dp1Fork;

    private String dp1Outgoing1;
    private String dp1Outgoing1Extender1;
    private String dp1Outgoing1Extender2;
    private String dp1Outgoing1Extender;
    private String dp1Outgoing1ExtenderCount;

    private String dp1Outgoing2;
    private String dp1Outgoing2Extender1;
    private String dp1Outgoing2Extender2;
    private String dp1Outgoing2Extender;
    private String dp1Outgoing2ExtenderCount;

    // DP 1 Merge
    private String dp1Merge;

    private String dp1MergeIncoming;
    private String dp1MergeIncomingExtender1;
    private String dp1MergeIncomingExtender2;
    private String dp1MergeIncomingExtender;
    private String dp1MergeIncomingExtenderCount;

    private String dp1MergeOutgoing;
    private String dp1MergeOutgoingExtender1;
    private String dp1MergeOutgoingExtender2;
    private String dp1MergeOutgoingExtender;
    private String dp1MergeOutgoingExtenderCount;

    // DP2 FORK
    private String dp2Incoming;
    private String dp2Fork;

    // DP2 Outgoing1
    private String dp2Outgoing1;
    private String dp2Outgoing1Extender1;
    private String dp2Outgoing1Extender2;
    private String dp2Outgoing1Extender;
    private String dp2Outgoing1ExtenderCount;

    // DP2 Outgoing2
    private String dp2Outgoing2;
    private String dp2Outgoing2Extender1;
    private String dp2Outgoing2Extender2;
    private String dp2Outgoing2ExtenderCount;
    private String dp2Outgoing2Extender;

    public LatEntry3DGJ(String junctionTemplate)
    {
        JunctionComponentParser jcp = new JunctionComponentParser();
        jcp.parseString(this, junctionTemplate);
    }

    public String getDp1IncomingExtender()
    {
        return dp1IncomingExtender;
    }

    public void setDp1IncomingExtender(String dp1IncomingExtender)
    {
        this.dp1IncomingExtender = dp1IncomingExtender;
    }

    public String getDp1Fork()
    {
        return dp1Fork;
    }

    public void setDp1Fork(String dp1Fork)
    {
        this.dp1Fork = dp1Fork;
    }

    public String getDp1Incoming()
    {
        return dp1Incoming;
    }

    public void setDp1Incoming(String dp1Incoming)
    {
        this.dp1Incoming = dp1Incoming;
    }

    public String getDp1IncomingExtenderCount()
    {
        return dp1IncomingExtenderCount;
    }

    public void setDp1IncomingExtenderCount(String dp1IncomingExtenderCount)
    {
        this.dp1IncomingExtenderCount = dp1IncomingExtenderCount;
    }

    public String getDp1Merge()
    {
        return dp1Merge;
    }

    public void setDp1Merge(String dp1Merge)
    {
        this.dp1Merge = dp1Merge;
    }

    public String getDp1MergeIncoming()
    {
        return dp1MergeIncoming;
    }

    public void setDp1MergeIncoming(String dp1MergeIncoming)
    {
        this.dp1MergeIncoming = dp1MergeIncoming;
    }

    public String getDp1MergeIncomingExtender1()
    {
        return dp1MergeIncomingExtender1;
    }

    public void setDp1MergeIncomingExtender1(String dp1MergeIncomingExtender1)
    {
        this.dp1MergeIncomingExtender1 = dp1MergeIncomingExtender1;
    }

    public String getDp1MergeIncomingExtender2()
    {
        return dp1MergeIncomingExtender2;
    }

    public void setDp1MergeIncomingExtender2(String dp1MergeIncomingExtender2)
    {
        this.dp1MergeIncomingExtender2 = dp1MergeIncomingExtender2;
    }

    public String getDp1MergeIncomingExtender()
    {
        return dp1MergeIncomingExtender;
    }

    public void setDp1MergeIncomingExtender(String dp1MergeIncomingExtender)
    {
        this.dp1MergeIncomingExtender = dp1MergeIncomingExtender;
    }

    public String getDp1MergeIncomingExtenderCount()
    {
        return dp1MergeIncomingExtenderCount;
    }

    public void setDp1MergeIncomingExtenderCount(String dp1MergeIncomingExtenderCount)
    {
        this.dp1MergeIncomingExtenderCount = dp1MergeIncomingExtenderCount;
    }

    public String getDp1MergeOutgoing()
    {
        return dp1MergeOutgoing;
    }

    public void setDp1MergeOutgoing(String dp1MergeOutgoing)
    {
        this.dp1MergeOutgoing = dp1MergeOutgoing;
    }

    public String getDp1MergeOutgoingExtender1()
    {
        return dp1MergeOutgoingExtender1;
    }

    public void setDp1MergeOutgoingExtender1(String dp1MergeOutgoingExtender1)
    {
        this.dp1MergeOutgoingExtender1 = dp1MergeOutgoingExtender1;
    }

    public String getDp1MergeOutgoingExtender2()
    {
        return dp1MergeOutgoingExtender2;
    }

    public void setDp1MergeOutgoingExtender2(String dp1MergeOutgoingExtender2)
    {
        this.dp1MergeOutgoingExtender2 = dp1MergeOutgoingExtender2;
    }

    public String getDp1MergeOutgoingExtender()
    {
        return dp1MergeOutgoingExtender;
    }

    public void setDp1MergeOutgoingExtender(String dp1MergeOutgoingExtender)
    {
        this.dp1MergeOutgoingExtender = dp1MergeOutgoingExtender;
    }

    public String getDp1MergeOutgoingExtenderCount()
    {
        return dp1MergeOutgoingExtenderCount;
    }

    public void setDp1MergeOutgoingExtenderCount(String dp1MergeOutgoingExtenderCount)
    {
        this.dp1MergeOutgoingExtenderCount = dp1MergeOutgoingExtenderCount;
    }

    public String getDp1Outgoing1()
    {
        return dp1Outgoing1;
    }

    public void setDp1Outgoing1(String dp1Outgoing1)
    {
        this.dp1Outgoing1 = dp1Outgoing1;
    }

    public String getDp1Outgoing1Extender1()
    {
        return dp1Outgoing1Extender1;
    }

    public void setDp1Outgoing1Extender1(String dp1Outgoing1Extender1)
    {
        this.dp1Outgoing1Extender1 = dp1Outgoing1Extender1;
    }

    public String getDp1Outgoing1Extender2()
    {
        return dp1Outgoing1Extender2;
    }

    public void setDp1Outgoing1Extender2(String dp1Outgoing1Extender2)
    {
        this.dp1Outgoing1Extender2 = dp1Outgoing1Extender2;
    }

    public String getDp1Outgoing1Extender()
    {
        return dp1Outgoing1Extender;
    }

    public void setDp1Outgoing1Extender(String dp1Outgoing1Extender)
    {
        this.dp1Outgoing1Extender = dp1Outgoing1Extender;
    }

    public String getDp1Outgoing1ExtenderCount()
    {
        return dp1Outgoing1ExtenderCount;
    }

    public void setDp1Outgoing1ExtenderCount(String dp1Outgoing1ExtenderCount)
    {
        this.dp1Outgoing1ExtenderCount = dp1Outgoing1ExtenderCount;
    }

    public String getDp1Outgoing2()
    {
        return dp1Outgoing2;
    }

    public void setDp1Outgoing2(String dp1Outgoing2)
    {
        this.dp1Outgoing2 = dp1Outgoing2;
    }

    public String getDp1Outgoing2Extender1()
    {
        return dp1Outgoing2Extender1;
    }

    public void setDp1Outgoing2Extender1(String dp1Outgoing2Extender1)
    {
        this.dp1Outgoing2Extender1 = dp1Outgoing2Extender1;
    }

    public String getDp1Outgoing2Extender2()
    {
        return dp1Outgoing2Extender2;
    }

    public void setDp1Outgoing2Extender2(String dp1Outgoing2Extender2)
    {
        this.dp1Outgoing2Extender2 = dp1Outgoing2Extender2;
    }

    public String getDp1Outgoing2Extender()
    {
        return dp1Outgoing2Extender;
    }

    public void setDp1Outgoing2Extender(String dp1Outgoing2Extender)
    {
        this.dp1Outgoing2Extender = dp1Outgoing2Extender;
    }

    public String getDp1Outgoing2ExtenderCount()
    {
        return dp1Outgoing2ExtenderCount;
    }

    public void setDp1Outgoing2ExtenderCount(String dp1Outgoing2ExtenderCount)
    {
        this.dp1Outgoing2ExtenderCount = dp1Outgoing2ExtenderCount;
    }

    public String getDp2Fork()
    {
        return dp2Fork;
    }

    public void setDp2Fork(String dp2Fork)
    {
        this.dp2Fork = dp2Fork;
    }

    public String getDp2Incoming()
    {
        return dp2Incoming;
    }

    public void setDp2Incoming(String dp2Incoming)
    {
        this.dp2Incoming = dp2Incoming;
    }

    public String getDp2Outgoing1()
    {
        return dp2Outgoing1;
    }

    public void setDp2Outgoing1(String dp2Outgoing1)
    {
        this.dp2Outgoing1 = dp2Outgoing1;
    }

    public String getDp2Outgoing1Extender1()
    {
        return dp2Outgoing1Extender1;
    }

    public void setDp2Outgoing1Extender1(String dp2Outgoing1Extender1)
    {
        this.dp2Outgoing1Extender1 = dp2Outgoing1Extender1;
    }

    public String getDp2Outgoing1Extender2()
    {
        return dp2Outgoing1Extender2;
    }

    public void setDp2Outgoing1Extender2(String dp2Outgoing1Extender2)
    {
        this.dp2Outgoing1Extender2 = dp2Outgoing1Extender2;
    }

    public String getDp2Outgoing1Extender()
    {
        return dp2Outgoing1Extender;
    }

    public void setDp2Outgoing1Extender(String dp2Outgoing1Extender)
    {
        this.dp2Outgoing1Extender = dp2Outgoing1Extender;
    }

    public String getDp2Outgoing1ExtenderCount()
    {
        return dp2Outgoing1ExtenderCount;
    }

    public void setDp2Outgoing1ExtenderCount(String dp2Outgoing1ExtenderCount)
    {
        this.dp2Outgoing1ExtenderCount = dp2Outgoing1ExtenderCount;
    }

    public String getDp2Outgoing2()
    {
        return dp2Outgoing2;
    }

    public void setDp2Outgoing2(String dp2Outgoing2)
    {
        this.dp2Outgoing2 = dp2Outgoing2;
    }

    public String getDp2Outgoing2Extender1()
    {
        return dp2Outgoing2Extender1;
    }

    public void setDp2Outgoing2Extender1(String dp2Outgoing2Extender1)
    {
        this.dp2Outgoing2Extender1 = dp2Outgoing2Extender1;
    }

    public String getDp2Outgoing2Extender2()
    {
        return dp2Outgoing2Extender2;
    }

    public void setDp2Outgoing2Extender2(String dp2Outgoing2Extender2)
    {
        this.dp2Outgoing2Extender2 = dp2Outgoing2Extender2;
    }

    public String getDp2Outgoing2Extender()
    {
        return dp2Outgoing2Extender;
    }

    public void setDp2Outgoing2Extender(String dp2Outgoing2Extender)
    {
        this.dp2Outgoing2Extender = dp2Outgoing2Extender;
    }

    public String getDp2Outgoing2ExtenderCount()
    {
        return dp2Outgoing2ExtenderCount;
    }

    public void setDp2Outgoing2ExtenderCount(String dp2Outgoing2ExtenderCount)
    {
        this.dp2Outgoing2ExtenderCount = dp2Outgoing2ExtenderCount;
    }

    public String getJunctionTemplate()
    {
        return junctionTemplate;
    }

    public void setJunctionTemplate(String junctionTemplate)
    {
        this.junctionTemplate = junctionTemplate;
    }

}
