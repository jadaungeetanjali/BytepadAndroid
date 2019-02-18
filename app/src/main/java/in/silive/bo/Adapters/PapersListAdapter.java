package in.silive.bo.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;

import android.widget.TextView;

import java.util.List;

import in.silive.bo.util.MappingSession;
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
    MappingSession mappingSession =new MappingSession();
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
                .inflate(R.layout.item_paperchanged, parent, false);

        return new PaperViewHolder(itemView);
    }
    public void addItems(List<PaperDetails> PaperModelList) {
        this.papersList = PaperModelList;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        //Typeface typeface= Typeface.createFromAsset(context.getAssets(),"font/middle.ttf");
        //Typeface typefaces= Typeface.createFromAsset(context.getAssets(),"font/bottom.ttf");
        final PaperDetails paper = this.getPapersList().get(position);
        //holder.tvPaperTitle.setTypeface(typeface);
        //holder.tvPaperCategory.setTypeface(typefaces);
        //holder.tvPaperType.setText(mappingPapeType.getvalues(paper.examTypeId));
       // Toast.makeText(context,mappingSession.getvalues(paper.sessionId),Toast.LENGTH_LONG).show();
        holder.tvPaperCategory.setText(mappingPapeType.getvalues(paper.examTypeId)+" "+"Semester "+
                mappingSession.getvalues(paper.sessionId) + " " + paper.paperType);
        holder.tvPaperTitle.setText(paper.subjectName);
        //holder.tvPaperSize.setText("10KB");
        int paperImgId;
       /* if (paper.Title.contains("doc") || paper.Title.contains("DOC") || paper.Title.contains("Doc"))
            paperImgId = R.drawable.doc;
        else if (paper.Title.contains("rtf") || paper.Title.contains("RTF") || paper.Title.contains("Rtf"))
            paperImgId = R.drawable.rtf;
        else
            paperImgId = R.drawable.pdf;
<<<<<<< HEAD
    */
//        holder.imageView.setImageResource(R.drawable.ficon);
       if (paper.downloaded) {
           //RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(20,25);

         //  holder.tvDownload.setLayoutParams(params);
            holder.tvDownload.setImageResource(R.drawable.viewbutton);
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Util.openDocument(context, paper.dwnldPath);
                    Log.i("downloadPath", paper.dwnldPath);
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
        LinearLayout constraintLayout;


        PaperViewHolder(View view) {
            super(view);
          //  tvPaperType=(TextView)view.findViewById(R.id.ivcode);
            tvPaperTitle = (TextView) view.findViewById(R.id.paper_title);
            tvPaperCategory = (TextView) view.findViewById(R.id.paper_category);
            //tvPaperSize = (TextView) view.findViewById(R.id.paper_size);

            tvDownload = (ImageView) view.findViewById(R.id.tvDownload);
            //imageView = (ImageView) view.findViewById(R.id.ivIcon);
           constraintLayout = (LinearLayout) view.findViewById(R.id.rview);

        }
    }


}
