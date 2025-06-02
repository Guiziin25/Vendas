package Product;

public class ProductImage {
    private String url;
    private Boolean mainImage;

    public ProductImage(String url, Boolean mainImage) {
        this.url = url;
        this.mainImage = mainImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getMainImage() {
        return mainImage;
    }

    public void setMainImage(Boolean mainImage) {
        this.mainImage = mainImage;
    }

}
