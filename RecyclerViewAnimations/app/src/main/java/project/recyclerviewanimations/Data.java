package project.recyclerviewanimations;

public class Data {

    private int imageId;
    private String loremIpsum;

    public Data(final int imageId, final String loremIpsum) {
        this.imageId = imageId;
        this.loremIpsum = loremIpsum;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }

    public String getLoremIpsum() {
        return loremIpsum;
    }

    public void setLoremIpsum(final String loremIpsum) {
        this.loremIpsum = loremIpsum;
    }
}
