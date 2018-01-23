/**
 * Created by SKKSC-Visitors on 19. 6. 2015.
 */
var ModelMesh = function(jsonComp){
    this.vertices = [];
    this.indices = jsonComp.roadSurfaceIndices;
    this.normals = [];
    // texture coordinates
    this.uvs = [];


    var jsonVertices = jsonComp.roadSurfaceVertices;
    for (var i = 0; i < jsonVertices.length; i++){
        this.vertices.push(
            jsonVertices[i].x,
            jsonVertices[i].z,
            jsonVertices[i].y
        );
        this.normals.push(0,0,-1);
        this.uvs.push(0,0);
    }
}

