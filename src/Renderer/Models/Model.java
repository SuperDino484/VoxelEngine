package Renderer.Models;

public class Model {

    private Mesh mesh;
    private int vaoID;
    private int iboID;

    public Model(Mesh mesh, int vaoID, int iboID) {
        this.mesh = mesh;
        this.vaoID = vaoID;
        this.iboID = iboID;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getIboID() {
        return iboID;
    }
}
