package com.hr.management.ui.components;

public interface FormComponent<T, V> {

    void resetFields();

    T readFields();

    void fillFieldsWith(V view);
}
