package net.theluckycoder.classmaker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Util.checkPermission(this))
            Util.requestPermission(this);
    }

    public void startActivity(View view) {
        int id = view.getId();
        if (id == R.id.itemClassBtn)
            startActivity(new Intent(MainActivity.this, ItemMaker.class));
        else if (id == R.id.armorItemClassBtn)
            startActivity(new Intent(MainActivity.this, ArmorItemMaker.class));
        else if (id == R.id.blockClassBtn)
            startActivity(new Intent(MainActivity.this, BlockMaker.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Util.PERMISSION_REQUEST_CODE:
                if (grantResults.length < 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.external_storage_permission_required, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
