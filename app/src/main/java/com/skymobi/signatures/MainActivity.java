package com.skymobi.signatures;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.skymobi.mopoui.binding.Binding;

import java.security.cert.X509Certificate;


public class MainActivity extends ActionBarActivity implements PackageListFragment.OnPackageSelectedListener {

    private Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = new Binding(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new PackageListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPackageSelected(PackageInfo pi) {
        String appName = String.valueOf(getPackageManager().getApplicationLabel(pi.applicationInfo));
        String packageName = pi.packageName;
        try {
            pi = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            // never
        }
        X509Certificate cert = mBinding.getX509Certificate(pi);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, HexFragment.newInstance(appName, packageName, cert.getSignature()))
                .addToBackStack(pi.packageName)
                .commit();
    }

}
