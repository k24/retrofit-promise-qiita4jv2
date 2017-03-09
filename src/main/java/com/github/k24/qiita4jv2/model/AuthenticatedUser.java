package com.github.k24.qiita4jv2.model;

/**
 * Created by k24 on 2017/02/13.
 */
public class AuthenticatedUser extends User {
    public long image_monthly_upload_limit;
    public long image_monthly_upload_remaining;
    public boolean team_only;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuthenticatedUser that = (AuthenticatedUser) o;

        if (image_monthly_upload_limit != that.image_monthly_upload_limit) return false;
        if (image_monthly_upload_remaining != that.image_monthly_upload_remaining) return false;
        return team_only == that.team_only;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (image_monthly_upload_limit ^ (image_monthly_upload_limit >>> 32));
        result = 31 * result + (int) (image_monthly_upload_remaining ^ (image_monthly_upload_remaining >>> 32));
        result = 31 * result + (team_only ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticatedUser{" +
                "image_monthly_upload_limit=" + image_monthly_upload_limit +
                ", image_monthly_upload_remaining=" + image_monthly_upload_remaining +
                ", team_only=" + team_only +
                "} " + super.toString();
    }
}
