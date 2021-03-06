package com.example.personalfinanceapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {
    DBOpenHelper myDb;
    static String selectedCategory;
    static int enteredAmount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentA newInstance(String param1, String param2) {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_a, container, false);

        Button bt_addTran = v.findViewById(R.id.bt_addTransaction);
        //when pressing the add transaction button
        bt_addTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Button","Add Transaction");
                addTransaction();
            }

        });

        return v;
    }

    private void addTransaction() {
        Log.e("Class","Add Transaction Class");

        //pop up dialog
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View myView = inflater.inflate(R.layout.layout_add_transaction, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText et_transactionAmount = myView.findViewById(R.id.et_amount);

        final Button bt_addTransaction = myView.findViewById(R.id.bt_addTransaction);
        final Button bt_cancelTransaction = myView.findViewById(R.id.bt_cancelTransaction);

        final Spinner spinner = myView.findViewById(R.id.spinner_addTransaction);

        //Spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.default_categories));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        //Button Click Listener
        //Button Add Transaction Listener
        bt_addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Button","Add!");

                //Check the values
                //Check the category
                if (!spinner.getSelectedItem().toString().equals("Choose your category")) {
                    //Get selected category
                    selectedCategory = spinner.getSelectedItem().toString();

                    //Check the amount
                    if (et_transactionAmount.getText().toString().length()>0) {

                        //Get entered amount
                        enteredAmount = Integer.parseInt(et_transactionAmount.getText().toString());
                        Log.e("Transaction", "Category: " + selectedCategory + ", Amounts: " + enteredAmount);

                        //need to save the category and amount later
                        //Using the ExternalStorage
                        //externalStorage();
                        //External Storage not that useful

                        //using MySQLite
                        writeToDatabase();

                        //after added the transaction
                        Toast.makeText(getActivity(), "Added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    } else
                        Toast.makeText(getActivity(), "Please enter the amount!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Please choose your category!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Button Cancel Transaction Listener
        bt_cancelTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //After pressed cancel button
                Log.e("Button","Cancel!");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void externalStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            savePrivateExternalFile();
        }
    }

    private void savePrivateExternalFile() {
        File folder = getActivity().getExternalFilesDir("Transactions");
        File myFile = new File (folder, "transactions.txt");
        FileOutputStream fos = null;

        try {
            if(myFile.createNewFile()) {
                Toast.makeText(getActivity(), "file created!", Toast.LENGTH_SHORT).show();
                fos = new FileOutputStream(myFile);

                //get current date
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                String content=formattedDate+","+selectedCategory+","+enteredAmount;

                fos.write(content.getBytes());
            } else {
                Toast.makeText(getActivity(), "file exists!", Toast.LENGTH_SHORT).show();
                //fos = new FileOutputStream(myFile);

                //get current date
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                String content=formattedDate+","+selectedCategory+","+enteredAmount;
                fos.write(content.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeToDatabase() {
        myDb = new DBOpenHelper(getActivity());

        //get current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        //use the DBOpenHelper insertData method
        boolean result = myDb.insertData(formattedDate, selectedCategory, enteredAmount);

        //check
        //Toast.makeText(getActivity(), "data inserted = "+result, Toast.LENGTH_LONG).show();
    }

}