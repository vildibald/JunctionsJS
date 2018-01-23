// Get the canvas element from our HTML below
var canvas = document.querySelector("#renderCanvas");
// Load the BABYLON 3D engine
var engine = new BABYLON.Engine(canvas, true);
//var shapes= [];
// -------------------------------------------------------------


var createSkybox = function (scene, camera) {
    var skybox = BABYLON.Mesh.CreateBox("skyBox", 10000.0, scene);
    var skyboxMaterial = new BABYLON.StandardMaterial("skyBox", scene);
    skyboxMaterial.backFaceCulling = false;
    skybox.infiniteDistance = true;
    var texture = new BABYLON.CubeTexture("textures/skybox/TropicalSunnyDay", scene);
    skyboxMaterial.reflectionTexture = texture;
    skyboxMaterial.reflectionTexture.coordinatesMode = BABYLON.Texture.SKYBOX_MODE;
    skyboxMaterial.diffuseColor = new BABYLON.Color3(0, 0, 0);
    skyboxMaterial.specularColor = new BABYLON.Color3(0, 0, 0);
    skybox.renderingGroupId = 0;
    skybox.material = skyboxMaterial;

    scene.registerBeforeRender(function () {
        skybox.position = camera.position
    });

    return skybox;
}

var createGround = function (scene) {
    var ground = BABYLON.Mesh.CreateGround("ground1", 500, 500, 2, scene);
    var groundMaterial = new BABYLON.StandardMaterial("ground", scene);
    var texture = new BABYLON.Texture("textures/ground/grass.png", scene);

    groundMaterial.diffuseTexture = texture;
    groundMaterial.diffuseTexture.uScale = 5.0;//Repeat 5 times on the Vertical Axes
    groundMaterial.diffuseTexture.vScale = 5.0;//Repeat 5 times on the Horizontal Axes
    groundMaterial.backFaceCulling = false;//Allways show the front and the back of an element
 
    groundMaterial.specularColor = new BABYLON.Color3(0, 0, 0);
    ground.material = groundMaterial;
    ground.renderingGroupId = 1;
    return ground;
};


var createFreeCamera = function (scene) {
    var camera = new BABYLON.FreeCamera("camera1", new BABYLON.Vector3(0, 5, -10), scene);
    camera.setTarget(BABYLON.Vector3.Zero());
    camera.attachControl(canvas, false);
    scene.activeCamera = camera;
    return camera;
}


var createRotateCamera = function (scene) {
    var camera = new BABYLON.ArcRotateCamera("ArcRotateCamera", 1, 0.8, 10, BABYLON.Vector3.Zero(), scene);

    camera.attachControl(canvas, false);
    scene.activeCamera = camera;
    return camera;
}

var createScene = function () {
    // Now create a basic Babylon Scene object
    var scene = new BABYLON.Scene(engine);
    // Change the scene background color to green.
    // scene.clearColor = new BABYLON.Color3(0.03, 0, 0.6);

    scene.fogColor = new BABYLON.Color3(0.9, 0.9, 0.85);
    scene.fogDensity = 0.01;
    // This creates and positions a free camera
    var camera = createRotateCamera(scene);
    // This creates a light, aiming 0,1,0 - to the sky.
    var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);
    // Dim the light a small amount
    light.intensity = .5;
    var skybox = createSkybox(scene, camera);


    // Let's try our built-in 'ground' shape. Params: name, width, depth, subdivisions, scene
    var ground = createGround(scene);
    ///////////////////////////////////////////////////////////////////////////
    //Skusanie jedneho meshu

    //var comp = null;
    $(document).ready(function () {
        $.getJSON("components/F0.10.16.88.json", function (json) {
            //comp = json;
            displayContents("nacitany json");
            var positions = [];
            var normals = [];
            var uvs = [];
            var jsonVertices = json.roadSurfaceVertices;
            for (var i = 0; i < jsonVertices.length; i++) {
                positions.push(
                    jsonVertices[i].x,
                    jsonVertices[i].z,
                    jsonVertices[i].y
                );
                normals.push(0, 0, -1);
                uvs.push(0, 0);
            }

            var indices = json.roadSurfaceIndices;
            var mesh = new BABYLON.Mesh("mesh", scene);
            mesh.setVerticesData(positions, BABYLON.VertexBuffer.PositionKind);
            mesh.setVerticesData(normals, BABYLON.VertexBuffer.NormalKind);
            mesh.setVerticesData(uvs, BABYLON.VertexBuffer.UVKind);
            mesh.setIndices(indices);
            mesh.position = new BABYLON.Vector3(0, 3, 0);
            mesh.diffuseColor = new BABYLON.Color3(0, 0, 0);
            mesh.specularColor = new BABYLON.Color3(0, 0, 0);

            //mesh.refl = new BABYLON.Color3(0, 0, 0);
            mesh.scaling.x = 1;
            mesh.scaling.y = 1;
            mesh.scaling.z = 1;
            mesh.backFaceCulling = false;
            mesh.renderingGroupId = 1;
            //mes
        });
    });
    //////////////////////////////////////////////////////////////////////

    return scene;
}; // End of createScene function
// -------------------------------------------------------------
// Now, call the createScene function that you just finished creating
var scene = createScene();
// Register a render loop to repeatedly render the scene

engine.runRenderLoop(function () {
    scene.render();
});
// Watch for browser/canvas resize events
window.addEventListener("resize", function () {
    engine.resize();
});


///////////////////////////////////
// Adding components

var generateLatEntryModel = function (entries, scene) {
    for (var i = 0; i < entries.length; i++) {
        createLatEntryModelComponent(entries[i], scene);
    }
}


var rotationZ = function (angle, vector) {
    var rx = vector.x * Math.cos(angle) - vector.y * Math.sin(angle);
    var ry = vector.x * Math.sin(angle) + vector.y * Math.cos(angle);
    return new BABYLON.Vector3(rx, ry, vector.z);
};
var computeNextPosition = function (compStartPos, compLocalStartPos, compLocalEndPos) {
    var move = compStartPos.position.clone();
    move.subtract(compLocalStartPos.position);
    var angleRad = signedAngle(compStartPos.direction, compLocalStartPos.direction);

    var endPoint = compLocalEndPos.clone();
    endPoint.add(move);
    endPoint.subtract(compStartPos.position);
   
    var angle = angleRad * 180 / Math.PI;
    var temp = rotationZ(angle, endPoint);
    endPoint = temp;
    endPoint.add(compStartPos.position);
    var endDirection = compLocalEndPos.direction.clone();
    endDirection = rotationZ(angle, endDirection);
    return new ComponentPosition(endDirection, endPoint);
   
};
var addComp = function (latEntryComponent, connectingPosition, scene) {
    var startPos = null;
    var endPos = null;
    if (!compIsListed(latEntryComponent)) {
        return connectingPosition;
    }
 
    var path = "components/" + latEntryComponent + ".json";
       
    $.ajaxSetup({
        async: false
    });
    var comp = null;
    $.getJSON(path, function (component) {comp =component});
            startPos = getCompStartPos(comp);
            endPos = getCompEndPos(comp);
            addCompToScene(comp, connectingPosition, startPos, scene)
     
    $.ajaxSetup({
        async: true
    });
  
    var newConnectingPos = computeNextPosition(connectingPosition, startPos, endPos);
    return newConnectingPos;
};

var getJunctionComponentByName = function (latEntryComp) {
    var component = null;
  
    $.ajaxSetup({
        async: false
    });
        var path = "components/" + latEntryComp + ".json";
     
        $.getJSON(path, function (comp) {
            component = comp;
            return comp;
        });
    $.ajaxSetup({
        async: true
    });

    return component;
}

var signedAngle = function (vector3_1, vector3_2) {

    var dot = BABYLON.Vector3.Dot(vector3_1, vector3_2);
    dot = dot / (vector3_1.length() * vector3_2.length());
    
    var rads = Math.acos(dot);
    
    //var angle = rads*180/Mathf.PI;
    return rads;
};
var addCompToScene = function (comp, connectToPosition, connectWhatPosition, scene) {
    var modelMesh = new ModelMesh(comp);
    var movePos = connectToPosition.position.clone();
    movePos.substract(connectWhatPosition.position);
    var angleRad = signedAngle(connectToPosition.direction, connectWhatPosition.direction);//*(-1);
    var angle = angleRad / Math.PI * 180;

    var mesh = new BABYLON.Mesh("mesh", scene);
    mesh.setVerticesData(positions, BABYLON.VertexBuffer.PositionKind);
    mesh.setVerticesData(normals, BABYLON.VertexBuffer.NormalKind);
    mesh.setVerticesData(uvs, BABYLON.VertexBuffer.UVKind);
    mesh.setIndices(indices);
   
    mesh.position = movePos;

    mesh.diffuseColor = new BABYLON.Color3(0, 0, 0);
    mesh.specularColor = new BABYLON.Color3(0, 0, 0);

    mesh.scaling.x = 1;
    mesh.scaling.y = 1;
    mesh.scaling.z = 1;
    mesh.backFaceCulling = false;
    mesh.renderingGroupId = 1;

};

var createLatEntryModelComponent = function (latEntry, scene) {
    // *** DP1 Incoming ***
    var compPos = new ComponentPosition(new BABYLON.Vector3(1, 0, 0), new BABYLON.Vector3(0, 0, 0));
    compPos = addComp(latEntry.dp1Incoming, compPos, scene);

    // *** DP1 Fork ***
    var dp1Fork = getJunctionComponentByName(latEntry.dp1Fork);
    var startPos = getCompStartPos(dp1Fork);
    //var en
    addCompToScene(dp1Fork, compPos, startPos, scene);
    var rightCompPos = computeNextPosition(compPos, startPos,
        new ComponentPosition(
            new BABYLON.Vector3(dp1Fork.rightAccesor.direction.x,
                dp1Fork.rightAccesor.direction.y,
                dp1Fork.rightAccesor.direction.z),
            new BABYLON.Vector3(dp1Fork.rightAccesor.point.x,
                dp1Fork.rightAccesor.point.y,
                dp1Fork.rightAccesor.point.z)
        )
    );
    var leftCompPos = computeNextPosition(compPos, startPos,
        new ComponentPosition(
            new BABYLON.Vector3(dp1Fork.leftAccesor.direction.x,
                dp1Fork.leftAccesor.direction.y,
                dp1Fork.leftAccesor.direction.z),
            new BABYLON.Vector3(dp1Fork.leftAccesor.point.x,
                dp1Fork.leftAccesor.point.y,
                dp1Fork.leftAccesor.point.z)
        )
    );

    // *** Right path ***
    var dp1Outgoing2 = getJunctionComponentByName(latEntry.dp1Outgoing2);
    startPos = getCompStartPos(dp1Outgoing2);
    addCompToScene(dp1Outgoing2, rightCompPos, startPos, scene);
    var endPos = getCompEndPos(dp1Outgoing2);
    rightCompPos = computeNextPosition(rightCompPos, startPos, endPos);
    var rightPathIsCloverleaf = latEntry.dp1Outgoing2.indexOf("CRu") > -1 ||
        latEntry.dp1Outgoing2.indexOf("CRd") > -1;
    rightCompPos = addComp(latEntry.dp1Outgoing2Extender1, rightCompPos, scene);
    rightCompPos = addComp(latEntry.dp1Outgoing2Extender2, rightCompPos, scene);

    // *** Left path ***
    var dp1Outgoing1 = getJunctionComponentByName(latEntry.dp1Outgoing1);
    startPos = getCompStartPos(dp1Outgoing1);
    addCompToScene(dp1Outgoing1, leftCompPos, startPos, scene);
    endPos = getCompEndPos(dp1Outgoing1);
    leftCompPos = computeNextPosition(leftCompPos, startPos, endPos);
    var leftPathIsCloverleaf = latEntry.dp1Outgoing1.indexOf("CLu") > -1 ||
        latEntry.dp1Outgoing2.indexOf("CLd") > -1;
    leftCompPos = addComp(latEntry.dp1Outgoing1Extender1, leftCompPos, scene);
    leftCompPos = addComp(latEntry.dp1Outgoing1Extender2, leftCompPos, scene);

    // *** cloverleaf ***
    if ((leftPathIsCloverleaf || rightPathIsCloverleaf) && compIsListed(latEntry.dp1Merge)) {
        var dp1Merge = getCompByName(latEntry.dp1Merge);
        var mergeOutgoing = null;
        var forkConnectingPos = null;
        var mergeIncomingConnectingPos = null;

        if (rightPathIsCloverleaf) {
            mergeOutgoing = rightCompPos;

            // connecting to fork left
            forkConnectingPos = new ComponentPosition(
                new BABYLON.Vector3(dp1Merge.leftAccesor.direction.x * (-1),
                    dp1Merge.leftAccesor.direction.y * (-1),
                    dp1Merge.leftAccesor.direction.z
                ),
                new BABYLON.Vector3(dp1Merge.leftAccesor.point.x,
                    dp1Merge.leftAccesor.point.y,
                    dp1Merge.leftAccesor.point.z
                )
            );
            mergeIncomingConnectingPos = new ComponentPosition(
                new BABYLON.Vector3(dp1Merge.rightAccesor.direction.x * (-1),
                    dp1Merge.rightAccesor.direction.y * (-1),
                    dp1Merge.rightAccesor.direction.z
                ),
                new BABYLON.Vector3(dp1Merge.rightAccesor.point.x,
                    dp1Merge.rightAccesor.point.y,
                    dp1Merge.rightAccesor.point.z
                )
            );
        } else {
            mergeOutgoing = leftCompPos;

            // connecting to fork right
            mergeIncomingConnectingPos = new ComponentPosition(
                new BABYLON.Vector3(dp1Merge.leftAccesor.direction.x * (-1),
                    dp1Merge.leftAccesor.direction.y * (-1),
                    dp1Merge.leftAccesor.direction.z
                ),
                new BABYLON.Vector3(dp1Merge.leftAccesor.point.x,
                    dp1Merge.leftAccesor.point.y,
                    dp1Merge.leftAccesor.point.z
                )
            );
            forkConnectingPos = new ComponentPosition(
                new BABYLON.Vector3(dp1Merge.rightAccesor.direction.x * (-1),
                    dp1Merge.rightAccesor.direction.y * (-1),
                    dp1Merge.rightAccesor.direction.z
                ),
                new BABYLON.Vector3(dp1Merge.rightAccesor.point.x,
                    dp1Merge.rightAccesor.point.y,
                    dp1Merge.rightAccesor.point.z
                )
            );
        }

        // *** merge fork ***
        addCompToScene(dp1Merge, mergeOutgoing, forkConnectingPos, scene);

        // *** merge incoming ***
        // set opposite direction
        mergeIncomingConnectingPos.direction.x *= -1;
        mergeIncomingConnectingPos.direction.y *= -1;

        mergeIncomingPos = computeNextPosition(mergeOutgoing, forkConnectingPos, mergeIncomingConnectingPos);
        mergeIncomingPos = addComp(latEntry.dp1MergeIncoming, mergeIncomingPos, scene);
        mergeIncomingPos = addComp(latEntry.dp1MergeIncomingExtender1, mergeIncomingPos, scene);
        mergeIncomingPos = addComp(latEntry.dp1MergeIncomingExtender2, mergeIncomingPos, scene);

        // *** merge outgoing ***
        mereOutgoingPos = computeNextPosition(mergeOutgoing, forkConnectingPos,
            new ComponentPosition(
                new BABYLON.Vector3(
                    dp1Merge.startAccesor.direction.x * (-1),
                    dp1Merge.startAccesor.direction.y * (-1),
                    dp1Merge.startAccesor.direction.z
                ),
                new BABYLON.Vector3(
                    dp1Merge.startAccesor.position.x,
                    dp1Merge.startAccesor.position.y,
                    dp1Merge.startAccesor.position.z
                )
            )
        );
        mergeOutgoing = addComp(latEntry.dp1MergeOutgoing, mergeOutgoing, scene);
        mergeOutgoing = addComp(latEntry.dp1MergeOutgoingExtender1, mergeOutgoing, scene);
        mergeOutgoing = addComp(latEntry.dp1MergeOutgoingExtender2, mergeOutgoing, scene);

        if(rightPathIsCloverleaf){
            rightCompPos = mergeOutgoing;
        }else{
            leftCompPos = mergeOutgoing;
        }
    }

    // *** 2DP ***
    if(compIsListed(latEntry.dp2Incoming)){
        // is there better way to find out DP2 side?
        var dpIsOnRight = latEntry.junctionTemplate.charAt(7)=='R';
        var dp2CompPos = null;

        if(dpIsOnRight){
            dp2CompPos = rightCompPos;
        }else{
            dp2CompPos=leftCompPos;
        }

        dp2CompPos =addComp(latEntry.dp2Incoming,dp2CompPos,scene);
        var dp2Fork = getJunctionComponentByName(latEntry.dp2Fork);
        addCompToScene(dp2Fork,dp2CompPos,getCompStartPos(dp2Fork),scene);

        var dp2RightCompPos = computeNextPosition(dp2CompPos,getCompStartPos(dp2Fork),
            new ComponentPosition(
                new BABYLON.Vector3(dp2Fork.rightAccesor.direction.x,
                    dp2Fork.rightAccesor.direcion.y,
                    dp2Fork.rightAccesor.direcion.z
                ),
                new BABYLON.Vector3(dp2Fork.rightAccesor.point.x,
                    dp2Fork.rightAccesor.point.y,
                    dp2Fork.rightAccesor.point.z
                )
            )
        );
        var dp2LeftCompPos = computeNextPosition(dp2CompPos,getCompStartPos(dp2Fork),
            new ComponentPosition(
                new BABYLON.Vector3(dp2Fork.leftAccesor.direction.x,
                    dp2Fork.leftAccesor.direcion.y,
                    dp2Fork.leftAccesor.direcion.z
                ),
                new BABYLON.Vector3(dp2Fork.leftAccesor.point.x,
                    dp2Fork.leftAccesor.point.y,
                    dp2Fork.leftAccesor.point.z
                )
            )
        );

        // *** 2DP Right path ***
        dp2RightCompPos = addComp(latEntry.dp2Outgoing2,dp2RightCompPos,scene);
        dp2RightCompPos = addComp(latEntry.dp2Outgoing2Extender1,dp2RightCompPos,scene);
        dp2RightCompPos = addComp(latEntry.dp2Outgoing2Extender2,dp2RightCompPos,scene);

        // *** 2DP Left path ***
        dp2LeftCompPos = addComp(latEntry.dp2Outgoing1,dp2LeftCompPos,scene);
        dp2LeftCompPos = addComp(latEntry.dp2Outgoing1Extender1,dp2LeftCompPos,scene);
        dp2LeftCompPos = addComp(latEntry.dp2Outgoing1Extender2,dp2LeftCompPos,scene);
    }
}

var compIsListed = function (latEntryComp) {
    return latEntryComp != null && latEntryComp != "" && latEntryComp != "\"\"";
}
var getCompStartPos = function (comp) {
    var direction =
        new BABYLON.Vector3(comp.startAccesor.direction.x,
            comp.startAccesor.direction.y,
            comp.startAccesor.direction.z);
    var position =
        new BABYLON.Vector3(comp.startAccesor.point.x,
            comp.startAccesor.point.y,
            comp.startAccesor.point.z);
    return new ComponentPosition(direction, position);
}

var getCompEndPos = function (comp) {
    var direction =
        new BABYLON.Vector3(comp.endAccesor.direction.x,
            comp.endAccesor.direction.y,
            comp.endAccesor.direction.z);
    var position =
        new BABYLON.Vector3(comp.endAccesor.point.x,
            comp.endAccesor.point.y,
            comp.endAccesor.point.z);
    return new ComponentPosition(direction, position);
}

/////////////////////////////////////////

function loadUserData(ev) {
    var csvFile = ev.target.files[0];
    if (!csvFile) {
        return;
    }


    var reader = new FileReader();
    var entries = [];
    reader.onerror = function (event) {
        console.error("File could not be read! Code " + event.target.error.code);
    };

    reader.onload = function (ev) {
        entries = parseEntries(ev.target.result);

        displayContents(entries);
        generateLatEntryModel(entries,scene);
    };
    reader.readAsText(csvFile);

}

function displayContents(contents) {
    var element = document.getElementById('file-content');
    element.innerHTML = contents;
}


document.getElementById('file-input')
    .addEventListener('change', loadUserData, false);

