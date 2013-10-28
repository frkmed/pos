package com.refresh.pos.ui;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.refresh.pos.R;
import com.refresh.pos.core.DataParser;
import com.refresh.pos.core.Inventory;
import com.refresh.pos.core.ProductCatalog;
import com.refresh.pos.database.NoDaoSetException;

public class ShowListActivity extends Activity {

	private ListView lisView1;
	private ProductCatalog productCatalog;
	List<HashMap<String, String>> stockList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showlist);

		try {
			productCatalog = Inventory.getInstance().getProductCatalog();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}

		
		stockList = DataParser.parseMap(productCatalog.getAllProduct());
		
		lisView1 = (ListView) findViewById(R.id.listView1);

		SimpleAdapter sAdap;
		sAdap = new SimpleAdapter(ShowListActivity.this, stockList,
				R.layout.activity_column, new String[] { "name",
						"barcode","sale_price" }, new int[] { R.id.ColProductID,
						R.id.ColName, R.id.ColBarcode });
		lisView1.setAdapter(sAdap);
		

		final Button addProductButton = (Button) findViewById(R.id.addNewProduct);

		addProductButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent newActivity = new Intent(ShowListActivity.this,AddActivity.class);
				startActivity(newActivity);
			}
		});
		final Button refreshButton = (Button) findViewById(R.id.refreshButton);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Log.v(">>>>","Refresh!");
				stockList = DataParser.parseMap(productCatalog.getAllProduct());
				SimpleAdapter sAdap;
				sAdap = new SimpleAdapter(ShowListActivity.this, stockList,
						R.layout.activity_column, new String[] { "name",
								"barcode","sale_price" }, new int[] { R.id.ColProductID,
								R.id.ColName, R.id.ColBarcode });
				lisView1.setAdapter(sAdap);
				
			}
		});

	}

}
