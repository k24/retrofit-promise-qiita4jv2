package com.github.k24.qiita4jv2.model;

import javax.annotation.Nonnull;

/**
 * Created by k24 on 2017/02/12.
 */
public class User {
    public String description;
    public String facebook_id;
    public int followees_count;
    public int followers_count;
    public String github_login_name;
    @Nonnull
    public String id;
    public int items_count;
    public String linkedin_id;
    public String location;
    public String name;
    public String organization;
    public long permanent_id;
    @Nonnull
    public String profile_image_url;
    public String twitter_screen_name;
    public String website_url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (followees_count != user.followees_count) return false;
        if (followers_count != user.followers_count) return false;
        if (items_count != user.items_count) return false;
        if (permanent_id != user.permanent_id) return false;
        if (description != null ? !description.equals(user.description) : user.description != null) return false;
        if (facebook_id != null ? !facebook_id.equals(user.facebook_id) : user.facebook_id != null) return false;
        if (github_login_name != null ? !github_login_name.equals(user.github_login_name) : user.github_login_name != null)
            return false;
        if (!id.equals(user.id)) return false;
        if (linkedin_id != null ? !linkedin_id.equals(user.linkedin_id) : user.linkedin_id != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (organization != null ? !organization.equals(user.organization) : user.organization != null) return false;
        if (!profile_image_url.equals(user.profile_image_url)) return false;
        if (twitter_screen_name != null ? !twitter_screen_name.equals(user.twitter_screen_name) : user.twitter_screen_name != null)
            return false;
        return website_url != null ? website_url.equals(user.website_url) : user.website_url == null;

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (facebook_id != null ? facebook_id.hashCode() : 0);
        result = 31 * result + followees_count;
        result = 31 * result + followers_count;
        result = 31 * result + (github_login_name != null ? github_login_name.hashCode() : 0);
        result = 31 * result + id.hashCode();
        result = 31 * result + items_count;
        result = 31 * result + (linkedin_id != null ? linkedin_id.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + (int) (permanent_id ^ (permanent_id >>> 32));
        result = 31 * result + profile_image_url.hashCode();
        result = 31 * result + (twitter_screen_name != null ? twitter_screen_name.hashCode() : 0);
        result = 31 * result + (website_url != null ? website_url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "description='" + description + '\'' +
                ", facebook_id='" + facebook_id + '\'' +
                ", followees_count=" + followees_count +
                ", followers_count=" + followers_count +
                ", github_login_name='" + github_login_name + '\'' +
                ", id='" + id + '\'' +
                ", items_count=" + items_count +
                ", linkedin_id='" + linkedin_id + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", organization='" + organization + '\'' +
                ", permanent_id=" + permanent_id +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", twitter_screen_name='" + twitter_screen_name + '\'' +
                ", website_url='" + website_url + '\'' +
                '}';
    }
}
