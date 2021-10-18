package com.alpha.sketcheditor

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.alpha.sketcheditor.databinding.ActivityMainBinding
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.mapping.view.SketchCreationMode
import com.esri.arcgisruntime.mapping.view.SketchEditor

// Ref: https://developers.arcgis.com/android/java/sample-code/sketch-editor/

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mapview: MapView by lazy { binding.map }

    private var defaultViewPoint: Viewpoint = Viewpoint(30.052697, 31.198192, 72000.0)

    private val sketchEditor: SketchEditor by lazy { SketchEditor() }
    private val graphicsOverlay: GraphicsOverlay by lazy { GraphicsOverlay() }

    private val pointButton: ImageButton by lazy { binding.pointButton }
    private val multiPointButton: ImageButton by lazy { binding.pointsButton }
    private val polylineButton: ImageButton by lazy { binding.polylineButton }
    private val polygonButton: ImageButton by lazy { binding.polygonButton }
    private val freehandLineButton: ImageButton by lazy { binding.freehandLineButton }
    private val freehandPolygonButton: ImageButton by lazy { binding.freehandPolygonButton }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupMap()

        pointButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.POINT)
        }

        multiPointButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.MULTIPOINT)
        }

        polylineButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.POLYLINE)
        }

        polygonButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.POLYGON)
        }

        freehandLineButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.FREEHAND_LINE)
        }

        freehandPolygonButton.setOnClickListener {
            reset()
            it.isSelected = true
            sketchEditor.start(SketchCreationMode.FREEHAND_POLYGON)
        }
    }

    private fun setupMap(){
        ArcGISRuntimeEnvironment.setApiKey(BuildConfig.API_KEY)
        mapview.map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)
        mapview.setViewpoint(defaultViewPoint)
        mapview.graphicsOverlays.add(graphicsOverlay)
        mapview.sketchEditor = sketchEditor

        mapview.map.addDoneLoadingListener {
            binding.toolbar.visibility = View.VISIBLE
        }
    }

    private fun reset(){
        pointButton.isSelected = false
        multiPointButton.isSelected = false
        polylineButton.isSelected = false
        polygonButton.isSelected = false
        freehandLineButton.isSelected = false
        freehandPolygonButton.isSelected = false
    }

    override fun onPause() {
        mapview.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapview.resume()
    }

    override fun onDestroy() {
        mapview.dispose()
        super.onDestroy()
    }
}