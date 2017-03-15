package com.github.k24.qiita4jv2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Available except Qiita:Team.
 * <p>
 * Created by k24 on 2017/03/14.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface PersonalOnly {
}
