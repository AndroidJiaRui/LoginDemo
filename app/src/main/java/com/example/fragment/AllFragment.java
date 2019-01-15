package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.logindemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WaitMoneyFragment waitMoneyFragment = new WaitMoneyFragment();
        WaitReceiverFragment waitReceiverFragment = new WaitReceiverFragment();
        WaitPJFragment waitPJFragment = new WaitPJFragment();
        AlreadyFragment alreadyFragment = new AlreadyFragment();
        transaction.add(R.id.f11,waitMoneyFragment).show(waitMoneyFragment);
        transaction.add(R.id.f22,waitReceiverFragment).show(waitReceiverFragment);
        transaction.add(R.id.f33,waitPJFragment).show(waitPJFragment);
        transaction.add(R.id.f44,alreadyFragment).show(alreadyFragment);
        transaction.commit();
    }

}
