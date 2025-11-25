package ch.heigvd.iict.daa.labo5

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private val imageCount = 100 // nbr of images to display
    private val colCount = 3 // nbr of columns in the grid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // depuis android 15 (sdk 35), le mode edge2edge doit être activé
        enableEdgeToEdge()

        // on spécifie le layout à afficher
        setContentView(R.layout.activity_main)

        // comme edge2edge est activé, l'application doit garder un espace suffisant pour la barre système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // la barre d'action doit être définie dans le layout, on la lie à l'activité
        setSupportActionBar(findViewById(R.id.toolbar))

        // TODO ...
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.main_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, colCount)

        val imageIds = (1..imageCount).toList()
        adapter = ImageAdapter(imageIds)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate the menu
        // this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear_cache -> {
                clearCache()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearCache() {
        // TODO: Step 4
    }

}