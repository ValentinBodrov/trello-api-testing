
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
    @SerializedName("email")
    @Expose
    public Object email;
    @SerializedName("idBoards")
    @Expose
    public List<String> idBoards = new ArrayList<String>();

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("avatarHash", avatarHash).append("bio", bio).append("bioData", bioData).append("confirmed", confirmed).append("fullName", fullName).append("initials", initials).append("memberType", memberType).append("status", status).append("url", url).append("username", username).append("email", email).append("idBoards", idBoards).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(initials).append(bio).append(fullName).append(confirmed).append(url).append(avatarHash).append(id).append(memberType).append(idBoards).append(bioData).append(email).append(status).append(username).toHashCode();
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
        return new EqualsBuilder().append(initials, rhs.initials).append(bio, rhs.bio).append(fullName, rhs.fullName).append(confirmed, rhs.confirmed).append(url, rhs.url).append(avatarHash, rhs.avatarHash).append(id, rhs.id).append(memberType, rhs.memberType).append(idBoards, rhs.idBoards).append(bioData, rhs.bioData).append(email, rhs.email).append(status, rhs.status).append(username, rhs.username).isEquals();
    }

}
