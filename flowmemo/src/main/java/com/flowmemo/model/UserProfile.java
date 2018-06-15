package com.flowmemo.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/10
 */

@Entity
public class UserProfile {

    @Id
    long boxId;

    String userName;
    String userEmail;
}


