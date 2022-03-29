package com.example.personalfinanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentB extends Fragment {
    DBOpenHelper myDb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentB() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentB.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentB newInstance(String param1, String param2) {
        FragmentB fragment = new FragmentB();
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
        View v = inflater.inflate(R.layout.fragment_b, container, false);

        ListView lv_transaction = v.findViewById(R.id.transactionListView);


        loadTransaction(lv_transaction);

        return v;
    }

    private void loadTransaction(ListView lv_transaction) {

        //lab7
        myDb = new DBOpenHelper(getActivity());

        Cursor res = myDb.getAllData();
        Transaction[] myTransaction = ConvertToTransaction(res);

        ArrayList transactions = new ArrayList();

        int resultCounts = res.getCount();
        if(resultCounts == 0){
            Toast.makeText(getActivity(),
                    "empty result", Toast.LENGTH_SHORT).show();
        }else {
            for(int i=0; i<resultCounts;i++){
                //Print the transactions data
                transactions.add(myTransaction[i].date + "\n" + myTransaction[i].category+"\n"+myTransaction[i].amount);
            }
        }
        /*backup code
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, transactions);
         */

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, transactions);

        //try to customize the layout if have time (using the adapter)

        lv_transaction.setAdapter(adapter);

    }

    @SuppressLint("Range")
    private Transaction[] ConvertToTransaction(Cursor cursor) {
        int resultCounts = cursor.getCount();
        //cursor.moveToFirst returns false if the cursor is empty
        //resultCounts == 0 ||
        if (!cursor.moveToFirst()) {
            return null;
        }
        Transaction transactions[] = new Transaction[resultCounts];

        for (int i=0; i<resultCounts; i++) {
            transactions[i] = new Transaction();
            transactions[i].id = cursor.getInt(1);
            transactions[i].date = cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_DATE));
            transactions[i].category = cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_CATEGORY));
            transactions[i].amount = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.KEY_AMOUNT));
            cursor.moveToNext();
        }
        return transactions;

    }

    class Transaction {
        int id;
        String date, category;
        int amount;

        Transaction() {
            id=-1;
            date="default-date";
            category="default-category";
            amount=0;
        }
    }

}