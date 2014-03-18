package com.skymobi.signatures;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wind.zhang on 14-3-17.
 */
public class PackageListFragment extends ListFragment {

    private OnPackageSelectedListener mOnPackageSelectedListener;
    private PackageListAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getParentFragment() instanceof OnPackageSelectedListener) {
            mOnPackageSelectedListener = (OnPackageSelectedListener) getParentFragment();
        } else if (activity instanceof OnPackageSelectedListener) {
            mOnPackageSelectedListener = (OnPackageSelectedListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnPackageSelectedListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mOnPackageSelectedListener != null) {
            mOnPackageSelectedListener.onPackageSelected((PackageInfo) l.getItemAtPosition(position));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PackageListAdapter(getActivity().getPackageManager(),
                getActivity().getLayoutInflater());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(mAdapter);
        onPackageInstalledOrRemoved();
    }

    public void onPackageInstalledOrRemoved() {
        mAdapter.scanAndNotify();
    }

    public interface OnPackageSelectedListener {
        void onPackageSelected(PackageInfo pi);
    }

    private class PackageListAdapter extends BaseAdapter {
        private PackageManager mPackageManager;
        private LayoutInflater mLayoutInflater;
        private List<PackageInfo> mPackageList;

        public PackageListAdapter(PackageManager packageManager, LayoutInflater layoutInflater) {
            mPackageManager = packageManager;
            mLayoutInflater = layoutInflater;
        }

        public void scanAndNotify() {
            mPackageList = mPackageManager.getInstalledPackages(0);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mPackageList == null ? 0 : mPackageList.size();
        }

        @Override
        public PackageInfo getItem(int position) {
            return mPackageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = mLayoutInflater.inflate(R.layout.package_list_item, parent, false);
                view.setTag(R.id.icon, view.findViewById(R.id.icon));
                view.setTag(R.id.app_name, view.findViewById(R.id.app_name));
                view.setTag(R.id.package_name, view.findViewById(R.id.package_name));
            }
            PackageInfo pi = getItem(position);
            ((ImageView) view.getTag(R.id.icon)).setImageDrawable(mPackageManager.getApplicationIcon(pi.applicationInfo));
            ((TextView) view.getTag(R.id.app_name)).setText(mPackageManager.getApplicationLabel(pi.applicationInfo));
            ((TextView) view.getTag(R.id.package_name)).setText(pi.packageName);
            return view;
        }
    }
}
