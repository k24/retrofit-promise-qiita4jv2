package com.github.k24.qiita4jv2;

import com.github.k24.qiita4jv2.model.*;
import net.arnx.jsonic.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k24 on 2017/03/03.
 */
public class TestFixture {

    public static AuthenticatedUser authenticatedUser() throws IOException {
        return JSON.decode(getResourceAsStream("authenticated_user"), AuthenticatedUser.class);
    }

    public static Comment comment() throws IOException {
        return JSON.decode(getResourceAsStream("comment"), Comment.class);
    }

    public static Item item() throws IOException {
        return JSON.decode(getResourceAsStream("item"), Item.class);
    }

    public static Reaction reaction() throws IOException {
        return JSON.decode(getResourceAsStream("reaction"), Reaction.class);
    }

    public static Tag tag() throws IOException {
        return JSON.decode(getResourceAsStream("tag"), Tag.class);
    }

    public static User user() throws IOException {
        return JSON.decode(getResourceAsStream("user"), User.class);
    }

    public static <T> List<T> asList(T prototype, int count) {
        ArrayList<T> list = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            list.add(prototype);
        }
        return list;
    }

    private static InputStream getResourceAsStream(String name) {
        return TestFixture.class.getResourceAsStream("/" + name + ".json");
    }
}
