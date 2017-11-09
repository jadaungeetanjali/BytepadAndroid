package in.silive.bo.Adapters;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.silive.bo.util.Mapping;
import in.silive.bo.R;
import in.silive.bo.Util;
import in.silive.bo.util.MappingPapeType;
import in.silive.bo.util.PaperDetails;

/**
 * Created by akriti on 6/8/16.
 */
public class PapersListAdapter extends RecyclerView.Adapter<PapersListAdapter.PaperViewHolder> {
    private Activity context;
    private List<PaperDetails> papersList;
    Mapping mapping=new Mapping();
    MappingPapeType mappingPapeType=new MappingPapeType();
    public PapersListAdapter(Activity context, List<PaperDetails> papersList) {
        this.papersList = papersList;
        this.context = context;

    }


    public List<PaperDetails> getPapersList() {
        return papersList;
    }


    @Override
    public int getItemCount() {
        return papersList.size();
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_paper, parent, false);

        return new PaperViewHolder(itemView);
    }
    public void addItems(List<PaperDetails> PaperModelList) {
        this.papersList = PaperModelList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {

        final PaperDetails paper = this.getPapersList().get(position);
        holder.tvPaperType.setText(mappingPapeType.getvalues(paper.examTypeId));
       // Toast.makeText(context,mapping.getvalues(paper.sessionId),Toast.LENGTH_LONG).show();
        holder.tvPaperCategory.setText(mapping.getvalues(paper.sessionId));
        holder.tvPaperTitle.setText(paper.subjectName);
        holder.tvPaperSize.setText("10KB");
        int paperImgId;
       /* if (paper.Title.contains("doc") || paper.Title.contains("DOC") || paper.Title.contains("Doc"))
            paperImgId = R.drawable.doc;
        else if (paper.Title.contains("rtf") || paper.Title.contains("RTF") || paper.Title.contains("Rtf"))
            paperImgId = R.drawable.rtf;
        else
            paperImgId = R.drawable.pdf;
<<<<<<< HEAD
    */
        holder.imageView.setImageResource(R.drawable.doc);
       if (paper.downloaded) {
            holder.tvDownload.setImageResource(R.drawable.view);
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Util.openDocument(context, paper.dwnldPath);
                }
            });
        } else {

           // holder.tvDownload.setText("Download");
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Util.downloadPaper(context, paper);
                }
            });

        }

    }

    class PaperViewHolder extends RecyclerView.ViewHolder {

        TextView tvPaperTitle;
        TextView tvPaperCategory;
        TextView tvPaperSize;
        ImageView tvDownload;
        TextView tvPaperType;
        ImageView imageView;
        ConstraintLayout constraintLayout;


        PaperViewHolder(View view) {
            super(view);
            tvPaperType=(TextView)view.findViewById(R.id.ivcode);
            tvPaperTitle = (TextView) view.findViewById(R.id.paper_title);
            tvPaperCategory = (TextView) view.findViewById(R.id.paper_category);
            tvPaperSize = (TextView) view.findViewById(R.id.paper_size);

            tvDownload = (ImageView) view.findViewById(R.id.tvDownload);
            imageView = (ImageView) view.findViewById(R.id.ivIcon);
           constraintLayout = (ConstraintLayout) view.findViewById(R.id.rview);

        }
    }


}
