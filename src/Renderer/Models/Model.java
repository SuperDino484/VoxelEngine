package Renderer.Models;

public class Model {

    private Mesh mesh;
    private int vaoID;

    public Model(Mesh mesh, int vaoID) {
        this.mesh = mesh;
        this.vaoID = vaoID;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getVaoID() {
        return vaoID;
    }
}
