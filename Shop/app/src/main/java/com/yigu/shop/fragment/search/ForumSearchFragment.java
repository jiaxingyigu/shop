package com.yigu.shop.fragment.search;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yigu.shop.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ForumSearchFragment extends Fragment {


    public ForumSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum_search, container, false);
    }

}
