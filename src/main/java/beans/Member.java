
package beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Member {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("avatarHash")
    @Expose
    public String avatarHash;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("bioData")
    @Expose
    public Object bioData;
    @SerializedName("confirmed")
    @Expose
    public Boolean confirmed;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("initials")
    @Expose
    public String initials;
    @SerializedName("memberType")
    @Expose
    public String memberType;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("avatarSource")
    @Expose
    public Object avatarSource;
    @SerializedName("email")
    @Expose
    public Object email;
    @SerializedName("gravatarHash")
    @Expose
    public Object gravatarHash;
    @SerializedName("idBoards")
    @Expose
    public List<String> idBoards = new ArrayList<String>();
    @SerializedName("idEnterprise")
    @Expose
    public Object idEnterprise;
    @SerializedName("loginTypes")
    @Expose
    public Object loginTypes;
    @SerializedName("oneTimeMessagesDismissed")
    @Expose
    public Object oneTimeMessagesDismissed;
    @SerializedName("prefs")
    @Expose
    public Object prefs;
    @SerializedName("uploadedAvatarHash")
    @Expose
    public Object uploadedAvatarHash;
    @SerializedName("idBoardsPinned")
    @Expose
    public Object idBoardsPinned;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("avatarHash", avatarHash).append("bio", bio).append("bioData", bioData).append("confirmed", confirmed).append("fullName", fullName).append("initials", initials).append("memberType", memberType).append("status", status).append("url", url).append("username", username).append("avatarSource", avatarSource).append("email", email).append("gravatarHash", gravatarHash).append("idBoards", idBoards).append("idEnterprise", idEnterprise).append("loginTypes", loginTypes).append("oneTimeMessagesDismissed", oneTimeMessagesDismissed).append("prefs", prefs).append("uploadedAvatarHash", uploadedAvatarHash).append("idBoardsPinned", idBoardsPinned).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(initials).append(oneTimeMessagesDismissed).append(bio).append(fullName).append(avatarSource).append(idBoardsPinned).append(loginTypes).append(confirmed).append(url).append(prefs).append(gravatarHash).append(avatarHash).append(uploadedAvatarHash).append(idEnterprise).append(id).append(memberType).append(idBoards).append(bioData).append(email).append(status).append(username).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Member) == false) {
            return false;
        }
        Member rhs = ((Member) other);
        return new EqualsBuilder().append(initials, rhs.initials).append(oneTimeMessagesDismissed, rhs.oneTimeMessagesDismissed).append(bio, rhs.bio).append(fullName, rhs.fullName).append(avatarSource, rhs.avatarSource).append(idBoardsPinned, rhs.idBoardsPinned).append(loginTypes, rhs.loginTypes).append(confirmed, rhs.confirmed).append(url, rhs.url).append(prefs, rhs.prefs).append(gravatarHash, rhs.gravatarHash).append(avatarHash, rhs.avatarHash).append(uploadedAvatarHash, rhs.uploadedAvatarHash).append(idEnterprise, rhs.idEnterprise).append(id, rhs.id).append(memberType, rhs.memberType).append(idBoards, rhs.idBoards).append(bioData, rhs.bioData).append(email, rhs.email).append(status, rhs.status).append(username, rhs.username).isEquals();
    }

}
