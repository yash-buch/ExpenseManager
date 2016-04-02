package com.binc.expensemanager.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.binc.expensemanager.R;
import com.binc.expensemanager.bean.DataBean;
import com.binc.expensemanager.helper.DataAccessor;
import com.binc.expensemanager.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yashbuch on 20/03/16.
 */
public class AddDialog extends DialogFragment {
    Button btn_add;

/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> units = new ArrayList<>();
        units.add("Kgs");
        units.add("Litres");
        units.add("Num");

        // Creating adapter for spinner
        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.add_layout, container, false);

        getDialog().setTitle("Add Item");

        btn_add = (Button) rootView.findViewById(R.id.btn_add);
        final EditText et_cat = (EditText) rootView.findViewById(R.id.et_cat);
        final EditText et_name_en = (EditText) rootView.findViewById(R.id.et_name_en);
        final EditText et_name_te = (EditText) rootView.findViewById(R.id.et_name_te);
        final EditText et_req_qty = (EditText) rootView.findViewById(R.id.et_req_qty);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBean data = new DataBean();
                //populate data bean
                Editable input = et_cat.getText();
                data.setCategory(input.toString());//category
                input = et_name_en.getText();
                data.setName_en(input.toString());//name_en
                input = et_name_te.getText();
                data.setName_te(input.toString());//name_te
                input = et_req_qty.getText();
                data.setReq_qty(input.toString());//req_qty

                DataAccessor mAccessor = DataAccessor.getDataAccessorInstance(getActivity());
                mAccessor.add(Constants.ProviderConstants.URL, data);

                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout((getResources().getDisplayMetrics().widthPixels), ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
