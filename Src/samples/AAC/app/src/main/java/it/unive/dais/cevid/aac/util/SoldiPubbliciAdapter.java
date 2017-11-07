package it.unive.dais.cevid.aac.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

import it.unive.dais.cevid.datadroid.lib.parser.SoldipubbliciParser;
import it.unive.dais.cevid.aac.R;

/**
 * Created by Fonto on 11/09/17.
 */

public class SoldiPubbliciAdapter extends RecyclerView.Adapter<SoldiPubbliciAdapter.SoldiPubbliciItem> {
    private static final String TAG = "SoldiPubbliciAdapter";
    private List<SoldipubbliciParser.Data> dataList;

    public SoldiPubbliciAdapter(List<SoldipubbliciParser.Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public SoldiPubbliciItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.soldipubblici_element, parent, false);
        return new SoldiPubbliciItem(itemView);

    }

    @Override
    public void onBindViewHolder(SoldiPubbliciItem holder, int position) {
        holder.importo.setText(String.valueOf(Double.parseDouble(dataList.get(position).importo_2016) / 100) + "€");
        holder.voceSpesa.setText(dataList.get(position).descrizione_codice);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class SoldiPubbliciItem extends RecyclerView.ViewHolder{
        private TextView voceSpesa, importo;

        public SoldiPubbliciItem(View itemView) {
            super(itemView);
            voceSpesa = (TextView) itemView.findViewById(R.id.descrizione_spesa_pubblica);
            importo = (TextView) itemView.findViewById(R.id.spesa_pubblica_2016);
        }
    }
}
