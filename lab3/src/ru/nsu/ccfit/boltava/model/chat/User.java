package ru.nsu.ccfit.boltava.model.chat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    private int mID;

    @XmlElement (name = "name")
    private String username;

    @XmlElement (name = "type")
    private String type;

    public User() {}

    public User(String username, String type) {
        this.username = username;
        this.type = type;
    }

    public int getID() {
        return mID;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mID == user.mID && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = mID;
        result = 31 * result + username.hashCode();
        return result;
    }
}
