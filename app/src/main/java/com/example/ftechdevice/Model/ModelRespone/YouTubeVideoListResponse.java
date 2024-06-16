package com.example.ftechdevice.Model.ModelRespone;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class YouTubeVideoListResponse {

    @SerializedName("kind")
    private String kind;

    @SerializedName("etag")
    private String etag;

    @SerializedName("items")
    private List<VideoItem> items;

    @SerializedName("pageInfo")
    private PageInfo pageInfo;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<VideoItem> getItems() {
        return items;
    }

    public void setItems(List<VideoItem> items) {
        this.items = items;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class VideoItem {

        @SerializedName("kind")
        private String kind;

        @SerializedName("etag")
        private String etag;

        @SerializedName("id")
        private String id;

        @SerializedName("snippet")
        private Snippet snippet;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String etag) {
            this.etag = etag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }

        public static class Snippet {

            @SerializedName("title")
            private String title;

            @SerializedName("description")
            private String description;

            @SerializedName("thumbnails")
            private Thumbnails thumbnails;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Thumbnails getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(Thumbnails thumbnails) {
                this.thumbnails = thumbnails;
            }
        }

        public static class Thumbnails {

            @SerializedName("default")
            private Thumbnail defaultThumbnail;

            @SerializedName("medium")
            private Thumbnail mediumThumbnail;

            @SerializedName("high")
            private Thumbnail highThumbnail;

            public Thumbnail getDefaultThumbnail() {
                return defaultThumbnail;
            }

            public void setDefaultThumbnail(Thumbnail defaultThumbnail) {
                this.defaultThumbnail = defaultThumbnail;
            }

            public Thumbnail getMediumThumbnail() {
                return mediumThumbnail;
            }

            public void setMediumThumbnail(Thumbnail mediumThumbnail) {
                this.mediumThumbnail = mediumThumbnail;
            }

            public Thumbnail getHighThumbnail() {
                return highThumbnail;
            }

            public void setHighThumbnail(Thumbnail highThumbnail) {
                this.highThumbnail = highThumbnail;
            }
        }

        public static class Thumbnail {

            @SerializedName("url")
            private String url;

            @SerializedName("width")
            private int width;

            @SerializedName("height")
            private int height;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }

    public static class PageInfo {

        @SerializedName("totalResults")
        private int totalResults;

        @SerializedName("resultsPerPage")
        private int resultsPerPage;

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public int getResultsPerPage() {
            return resultsPerPage;
        }

        public void setResultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
        }
    }

    public static class ContentDetails {

        @SerializedName("duration")
        private String duration;

        @SerializedName("dimension")
        private String dimension;

        @SerializedName("definition")
        private String definition;

        @SerializedName("caption")
        private String caption;

        @SerializedName("licensedContent")
        private boolean licensedContent;

        @SerializedName("projection")
        private String projection;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public boolean isLicensedContent() {
            return licensedContent;
        }

        public void setLicensedContent(boolean licensedContent) {
            this.licensedContent = licensedContent;
        }

        public String getProjection() {
            return projection;
        }

        public void setProjection(String projection) {
            this.projection = projection;
        }
    }

    public static class Status {

        @SerializedName("uploadStatus")
        private String uploadStatus;

        @SerializedName("privacyStatus")
        private String privacyStatus;

        @SerializedName("license")
        private String license;

        @SerializedName("embeddable")
        private boolean embeddable;

        @SerializedName("publicStatsViewable")
        private boolean publicStatsViewable;

        @SerializedName("madeForKids")
        private boolean madeForKids;

        public String getUploadStatus() {
            return uploadStatus;
        }

        public void setUploadStatus(String uploadStatus) {
            this.uploadStatus = uploadStatus;
        }

        public String getPrivacyStatus() {
            return privacyStatus;
        }

        public void setPrivacyStatus(String privacyStatus) {
            this.privacyStatus = privacyStatus;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public boolean isEmbeddable() {
            return embeddable;
        }

        public void setEmbeddable(boolean embeddable) {
            this.embeddable = embeddable;
        }

        public boolean isPublicStatsViewable() {
            return publicStatsViewable;
        }

        public void setPublicStatsViewable(boolean publicStatsViewable) {
            this.publicStatsViewable = publicStatsViewable;
        }

        public boolean isMadeForKids() {
            return madeForKids;
        }

        public void setMadeForKids(boolean madeForKids) {
            this.madeForKids = madeForKids;
        }
    }

    public static class Statistics {

        @SerializedName("viewCount")
        private String viewCount;

        @SerializedName("likeCount")
        private String likeCount;

        @SerializedName("favoriteCount")
        private String favoriteCount;

        @SerializedName("commentCount")
        private String commentCount;

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(String favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }
    }
}
