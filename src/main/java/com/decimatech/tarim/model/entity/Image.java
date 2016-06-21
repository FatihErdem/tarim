package com.decimatech.tarim.model.entity;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "maintain_id")
    private Long maintainId;

    @Column(name = "image_url")
    private String imageUrl;

    public Image() {
    }

    public Image(Long maintainId, String imageUrl) {
        this.maintainId = maintainId;
        this.imageUrl = imageUrl;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
