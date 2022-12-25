package com.hr.management.ui.components;

import com.hr.management.ui.client.view.EmployeeView;

public interface FormComponent<T> {

    FormComponent<T> resetFields();

    T readFields();

    FormComponent<T> fillFieldsWith(EmployeeView employeeView);
}
