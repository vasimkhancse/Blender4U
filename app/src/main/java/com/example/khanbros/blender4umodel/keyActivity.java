package com.example.khanbros.blender4umodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jawnnypoo.physicslayout.Physics;
import com.jawnnypoo.physicslayout.PhysicsLinearLayout;

import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.providers.TableDataRowBackgroundProvider;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

public class keyActivity extends Activity {
Context context=keyActivity.this;
    private static final String[][] DATA_TO_SHOW = { { "This", "is", "a", "test" },
            { "and", "a", "second", "test" } ,{ "This", "is", "3rd", " test" } };

    private static final String[] TABLE_HEADERS = { "VASIM", "KHAN", "WOW", "NO!!!" };


ExpandableListView e;TextView t,tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        e=findViewById(R.id.e);
        List<String> heading=new ArrayList<String>();
        List<String> Basenavigation=new ArrayList<String>();
        List<String> Basenavigation2=new ArrayList<String>();
        List<String> common=new ArrayList<String>();
        List<String> common2=new ArrayList<String>();
        List<String> whilemove=new ArrayList<String>();
        List<String> whilemove2=new ArrayList<String>();
        List<String> inobjectmode=new ArrayList<String>();
        List<String> inobjectmode2=new ArrayList<String>();
        List<String> selection=new ArrayList<String>();
        List<String> selection2=new ArrayList<String>();
        List<String> changeview=new ArrayList<String>();
        List<String>changeview2=new ArrayList<String>();
        List<String> ineditmode=new ArrayList<String>();
        List<String>ineditmode2=new ArrayList<String>();
        List<String> rendering=new ArrayList<String>();
        List<String> rendering2=new ArrayList<String>();
        List<String> nodeeditor=new ArrayList<String>();
        List<String> nodeeditor2=new ArrayList<String>();
        List<String> sculpting=new ArrayList<String>();
        List<String>sculpting2=new ArrayList<String>();
        List<String> editingcurves=new ArrayList<String>();
        List<String>editingcurves2=new ArrayList<String>();
        List<String> Animation=new ArrayList<String>();
        List<String>Animation2=new ArrayList<String>();
        List<String> posingmode=new ArrayList<String>();
        List<String> posingmode2=new ArrayList<String>();
        List<String> armatures=new ArrayList<String>();
        List<String> armatures2=new ArrayList<String>();
        List<String> funtoknow=new ArrayList<String>();
        List<String>funtoknow2=new ArrayList<String>();
        List<String> timeline=new ArrayList<String>();
        List<String>timeline2=new ArrayList<String>();


        HashMap<String,List<String>> child=new HashMap<String, List<String>>();
        HashMap<String,List<String>> child2=new HashMap<String, List<String>>();

        for(String head:getResources().getStringArray(R.array.heading)){
            heading.add(head);

        }
        for(String b:getResources().getStringArray(R.array.BasicNavigation)){
           Basenavigation.add(b);

        }
        for(String b:getResources().getStringArray(R.array.BasicNavigation2)){
            Basenavigation2.add(b);

        }

        for(String b:getResources().getStringArray(R.array.common)){
           common.add(b);

        }
        for(String b:getResources().getStringArray(R.array.common2)){
           common2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.whileMoving)){
            whilemove.add(b);

        }
        for(String b:getResources().getStringArray(R.array.whileMoving2)){
            whilemove2.add(b);

        }

        for(String b:getResources().getStringArray(R.array.inobjectmode)){
            inobjectmode.add(b);

        }
        for(String b:getResources().getStringArray(R.array.inobjectmode2)){
            inobjectmode2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.selection)){
            selection.add(b);

        }
        for(String b:getResources().getStringArray(R.array.selection2)){
            selection2.add(b);

        }

        for(String b:getResources().getStringArray(R.array.changingview)){
            changeview.add(b);

        }
        for(String b:getResources().getStringArray(R.array.changingview2)){
            changeview2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.ineditmode)){
            ineditmode.add(b);

        }
        for(String b:getResources().getStringArray(R.array.ineditmode2)){
            ineditmode2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.rendering)){
           rendering.add(b);

        }
        for(String b:getResources().getStringArray(R.array.rendering2)){
            rendering2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.nodeeditor)){
            nodeeditor.add(b);

        }
        for(String b:getResources().getStringArray(R.array.nodeeditor2)){
            nodeeditor2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.sculpting)){
            sculpting.add(b);

        }
        for(String b:getResources().getStringArray(R.array.sculpting2)){
            sculpting2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.editingcurves)){
            editingcurves.add(b);

        }
        for(String b:getResources().getStringArray(R.array.editingcurves2)){
            editingcurves2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.animation)){
            Animation.add(b);

        }
        for(String b:getResources().getStringArray(R.array.animation2)){
            Animation2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.posingmode)){
           posingmode.add(b);

        }
        for(String b:getResources().getStringArray(R.array.posingmode2)){
            posingmode2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.armatures)){
           armatures.add(b);

        }
        for(String b:getResources().getStringArray(R.array.armatures2)){
            armatures2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.funtoknow)){
           funtoknow.add(b);

        }
        for(String b:getResources().getStringArray(R.array.funtoknow2)){
            funtoknow2.add(b);

        }
        for(String b:getResources().getStringArray(R.array.timeline)){
            timeline.add(b);

        }
        for(String b:getResources().getStringArray(R.array.timeline2)){
            timeline2.add(b);

        }


        child.put(heading.get(0),Basenavigation);
        child.put(heading.get(1),common);
        child.put(heading.get(2),whilemove);
        child.put(heading.get(3),inobjectmode);
        child.put(heading.get(4),selection);
        child.put(heading.get(5),changeview);
        child.put(heading.get(6),ineditmode);
        child.put(heading.get(7),rendering);
        child.put(heading.get(8),nodeeditor);
        child.put(heading.get(9),sculpting);
        child.put(heading.get(10),editingcurves);
        child.put(heading.get(11),Animation);
        child.put(heading.get(12),posingmode);
        child.put(heading.get(13),armatures);
        child.put(heading.get(14),funtoknow);
        child.put(heading.get(15),timeline);

        child2.put(heading.get(0),Basenavigation2);
        child2.put(heading.get(1),common2);
        child2.put(heading.get(2),whilemove2);
        child2.put(heading.get(3),inobjectmode2);
        child2.put(heading.get(4),selection2);
        child2.put(heading.get(5),changeview2);
        child2.put(heading.get(6),ineditmode2);
        child2.put(heading.get(7),rendering2);
        child2.put(heading.get(8),nodeeditor2);
        child2.put(heading.get(9),sculpting2);
        child2.put(heading.get(10),editingcurves2);
        child2.put(heading.get(11),Animation2);
        child2.put(heading.get(12),posingmode2);
        child2.put(heading.get(13),armatures2);
        child2.put(heading.get(14),funtoknow2);
        child2.put(heading.get(15),timeline2);

        expandlistadapter ex=new expandlistadapter(heading,child,child2,this);
        e.setAdapter(ex);

        t=findViewById(R.id.textView11);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for (int i=0;i<=15;i++){
                   e.expandGroup(i);
               }

            }
        });
        tt=findViewById(R.id.textView12);
        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=15;i++){
                    e.collapseGroup(i);
                }
            }
        });


        //physicslayout();
         //tableview();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        startActivity(new Intent(context,learnActivity.class));
    }

    private void tableview() {
        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));

        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        int colorEvenRows = getResources().getColor(android.R.color.holo_green_light);
        int colorOddRows = getResources().getColor(android.R.color.holo_blue_light);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        // tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.similarRowColor(colorOddRows));
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {


                // Toast.makeText(keyActivity.this, clickedData[rowIndex], Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void physicslayout() {
        PhysicsLinearLayout p=findViewById(R.id.pl);
        p.getPhysics().enableFling();

        p.getPhysics().setOnFlingListener(new Physics.OnFlingListener() {
            @Override
            public void onGrabbed(View view) {
                if(view==findViewById(R.id.imageView3))
                    Toast.makeText(context," podaaaaa",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReleased(View releasedView) {

            }
        });
        p.getPhysics().setOnCollisionListener(new Physics.OnCollisionListener() {
            @Override
            public void onCollisionEntered(int viewIdA, int viewIdB) {
                if(viewIdA==R.id.imageView3&&viewIdB==R.id.imageView5)

                    Toast.makeText(context," wowww",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCollisionExited(int viewIdA, int viewIdB) {

            }
        });
    }
}
