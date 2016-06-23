package com.smartdev.classmaker;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.smartdev.classmaker.utils.Utils;

import java.io.File;

public class BlockMaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        File dir = new File(Utils.path);
        dir.mkdirs();
    }

    public void createMod (View view) {
        //Defines
        EditText className = (EditText) findViewById(R.id.blockClassNameTxt);
        EditText descriptionId = (EditText) findViewById(R.id.blockDescriptionIdTxt);
        EditText category = (EditText) findViewById(R.id.blockCategoryTxt);
        EditText material = (EditText) findViewById(R.id.blockMaterialTxt);
        EditText destroyTime = (EditText) findViewById(R.id.blockDestroyTimeTxt);


        //Asserts
        assert className != null;
        assert descriptionId != null;
        assert category != null;
        assert material != null;
        assert destroyTime != null;

        //Strings and Integers
        String classNameTxt = className.getText().toString();
        String descriptionIdTxt = className.getText().toString();
        String categoryTxt = category.getText().toString();
        String materialTxt = material.getText().toString();
        String destroyTimeTxt = destroyTime.getText().toString();

        if (categoryTxt.matches("1"))
            categoryTxt = "BLOCKS";
        else if (categoryTxt.matches("2"))
            categoryTxt = "DECORATIONS";
        else if (categoryTxt.matches("3"))
            categoryTxt = "TOOLS";
        else if (categoryTxt.matches("4"))
            categoryTxt = "ITEMS";


        //Code
        File headerFile = new File (Utils.path + (classNameTxt + ".h"));
        File sourceFile = new File (Utils.path + (classNameTxt + ".cpp"));

        String [] headerFileString = String.valueOf("#pragma once\n\n" +
                "#include \"com/mojang/minecraftpe/world/level/block/Block.h\"\n\n" +
                "class " + classNameTxt + " : public Block {\n" +
                "public:\n" +
                "\t" + classNameTxt + "(int blockId);\n" +
                "};").split(System.getProperty("line.separator"));

        String [] sourceFileString = String.valueOf("#include \"" + classNameTxt + ".h\"\n\n" +
                "#include \"com/mojang/minecraftpe/CreativeItemCategory.h\"\n\n" +
                classNameTxt + "::" + classNameTxt + "(int blockId) : Block(\"" + descriptionIdTxt + "\", " + "blockId, Material::getMaterial(MaterialType::" + materialTxt + ")) {\n" +
                "\tcreativeCategory = CreativeItemCategory::" + categoryTxt + ";\n" +
                "\tsetDestroyTime(" + destroyTimeTxt + "F);\n" +
                "}").split(System.getProperty("line.separator"));

        if (!classNameTxt.matches("")) {
            Utils.Save(headerFile, headerFileString);
            Utils.Save(sourceFile, sourceFileString);
            Snackbar.make(view, (classNameTxt + R.string.class_successfully_generated), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(view, R.string.error_empty_mod_name, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
