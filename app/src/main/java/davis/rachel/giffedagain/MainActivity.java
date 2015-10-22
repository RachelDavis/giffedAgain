package davis.rachel.giffedagain;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.Image;
        import android.net.Uri;
        import android.provider.MediaStore;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;

        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void go(View view){
        Bitmap i = BitmapFactory.decodeResource(getResources(),R.drawable.screen);
        Bitmap j = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
        byte[] b = make(i, j);

        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bmp , "signature", null);
        Uri sigURI = Uri.parse(path);
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
        if(sigURI != null){
            emailIntent.putExtra(Intent.EXTRA_STREAM, sigURI);
        }
        this.startActivity(Intent.createChooser(emailIntent, "Sending Email...."));

    }
    public byte[] make(Bitmap i, Bitmap j) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GifSequenceWriter g = new GifSequenceWriter();
        g.start(bos);
        g.addFrame(i);
        g.addFrame(j);
        g.finish();
        return bos.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
