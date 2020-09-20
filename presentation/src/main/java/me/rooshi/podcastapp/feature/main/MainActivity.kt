package me.rooshi.podcastapp.feature.main;

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
//import com.jakewharton.rxbinding4.material.itemSelections
import com.jakewharton.rxbinding4.view.clicks

import javax.inject.Inject

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.dismissKeyboard
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.MainActivityBinding
import me.rooshi.podcastapp.feature.main.explore.ExploreFragment
import me.rooshi.podcastapp.feature.main.social.SocialFragment
import me.rooshi.podcastapp.feature.main.subscriptions.SubscriptionsFragment

//ACTIVITY JUST DOES THE UI PARTS AND SETTING UP THE INTENTS
// THE ACTUAL LOGIC IS IN THE VIEWMODEL CLASS
@AndroidEntryPoint
class MainActivity : MyThemedActivity(), MainView {

    @Inject lateinit var navigator : Navigator

    @Inject lateinit var myFragmentFactory: MyFragmentFactory

    //need to inject these. on second thought if i use fragmentfactory I might not be able to
    val exploreFragment = ExploreFragment()
    val subscriptionsFragment = SubscriptionsFragment()
    val socialFragment = SocialFragment()

    override val castIntent by lazy { binding.cast.clicks() }
    override val profileIntent by lazy { binding.profileImage.clicks() }
    //override val bottomNavigationIntent by lazy { binding.bottomNavigationView.itemSelections() }

    private val binding by viewBinding(MainActivityBinding::inflate)
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(binding.root)
        viewModel.bindView(this)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.fragmentFactory = myFragmentFactory

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            setFragmentContainer(item)
        }

        //move to if logged in on app start
        //navigator.startLoginActivity()
    }

    private fun setFragmentContainer(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.bottom_nav_social -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, socialFragment, "social")
                    .commit()

            R.id.bottom_nav_subscriptions -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, subscriptionsFragment, "subscriptions")
                    .commit()

            R.id.bottom_nav_explore -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, exploreFragment, "explore")
                    .commit()
            else -> return false
        }
        return true
    }


    override fun render(state: MainState) {
        if (state.hasError) {
            finish()
            return
        }

        //this is where all the binding visibility sets go, based on the MainState
        //binding.toolbarTitle.setVisible(state.whatever)

        //then set the actual values for the views
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /*
    private FirebaseAuth firebaseAuth;
    private FirebaseFunctions firebaseFunctions;

    boolean playing = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    List<SearchAdapter.Podcast> results = new ArrayList<SearchAdapter.Podcast>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    JSONObject searchResponse;
    boolean kailabtnclicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*
        //1. see if someone is logged in. If not, go to login Activity
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //if (currentUser == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        //}

        //move to Intent onreturn if still no logged in start login intent again
        setupRecyclerView();
        initPlayer();
         */
    }

    public void setupRecyclerView() {
        results.add(new SearchAdapter.Podcast("Search for a podcast", "Artist", ""));
        recyclerView = findViewById(R.id.podcastList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final Context context = this;
        // specify an adapter (see also next example)
        mAdapter = new SearchAdapter(this, results, new SearchAdapter.RecyclerViewClickListener() {
            @Override public void onClick(View view, int position) {
                Intent intent = new Intent(context, PodcastActivity.class);
                intent.putExtra("trackName", results.get(position).track);
                intent.putExtra("artistName", results.get(position).artist);
                intent.putExtra("artworkURL", results.get(position).artwork);
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void RR3Intent(View view) {
        Intent intent = new Intent(this, rr3.class);
        startActivity(intent);
    }

    public void searchTerm(View view) {
        EditText inputText = findViewById(R.id.textInput);
        String toSearch = inputText.getText().toString();
        //String toSearch = "money";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseItunesUrl = "https://itunes.apple.com/search?media=podcast";
        Uri uri = Uri.parse(baseItunesUrl);
        uri = uri.buildUpon().appendQueryParameter("term", toSearch).build();
        String url = uri.toString();

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("search");

                        myRef.setValue(response);

                        try {
                            searchResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(response);
                        }

                        if (searchResponse == null) return;
                        results.clear();
                        mAdapter.notifyDataSetChanged();
                        try {
                            JSONArray jResults = searchResponse.getJSONArray("results");
                            for (int i = 0; i < jResults.length(); i++) {
                                JSONObject jResult = jResults.getJSONObject(i);
                                String trackName = jResult.getString("trackName");
                                String artistName = jResult.getString("artistName");
                                String artwork = jResult.getString("artworkUrl100");
                                results.add(0, new SearchAdapter.Podcast(trackName, artistName, artwork));
                                mAdapter.notifyItemInserted(0);
                            }
                        } catch (JSONException e) {
                            System.out.println("JSON Error on parsing search results");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void login(View view) {
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivity(intent);
    }

    public void loginWithGoogle(View view) {
        Intent intent = new Intent(this, GoogleLoginActivity.class);
        startActivity(intent);
    }
  
    private void initPlayer() {
        String podcastEpisode = "https://locator.simplecastcdn.com/e7ec86c9-5b4f-4c1c-af7b-0957921e175d/dcb5d4e2-c757-4b6b-ae0c-691b26f70e7a.mp3";
        Uri uri = Uri.parse(podcastEpisode);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("", "initPlayer: ", e);
        }
    }
    public void playPause(View view) {

        if (!playing) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
        playing = !playing;
    }

    public void recyclerDisplay(View view) {
        if (searchResponse == null) return;
        results.clear();
        mAdapter.notifyDataSetChanged();
        try {
            JSONArray jResults = searchResponse.getJSONArray("results");
            for (int i = 0; i < jResults.length(); i++) {
                JSONObject jResult = jResults.getJSONObject(i);
                String trackName = jResult.getString("trackName");
                String artistName = jResult.getString("artistName");
                String artwork = jResult.getString("artworkUrl100");
                results.add(0, new SearchAdapter.Podcast(trackName, artistName, artwork));
                mAdapter.notifyItemInserted(0);
            }
        } catch (JSONException e) {
            System.out.println("JSON Error on parsing search results");
        }

    }

    public void toggletext(View view){

        final TextView testTextView = findViewById(R.id.testTextView);

        final Button kailabtn = (Button) findViewById(R.id.kaila);
        kailabtnclicked = !kailabtnclicked;
        if(kailabtnclicked == true){
            kailabtn.setText("toggle click!");
        }else{
            kailabtn.setText("kaila");
        }

        EditText inputText = findViewById(R.id.textInput);
        String toSearch = inputText.getText().toString();

        getSearch(toSearch)
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }

                            // [START_EXCLUDE]
                            Log.w("notsurewhatthisis", "getSearch:onFailure", e);
//                            showSnackbar("An error occurred.");
                            return;
                            // [END_EXCLUDE]
                        }// if the task is successful

                        // [START_EXCLUDE]
                        String result = task.getResult();
                        testTextView.setText("This is the result from the firebase function: " + result);
                        // [END_EXCLUDE]
                    }
                });
    }

    private Task<String> getSearch(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);
        return firebaseFunctions
                .getHttpsCallable("getSearch")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = task.getResult().getData().toString();
                        return result;
                    }
                }
                );
    }

     */
}
