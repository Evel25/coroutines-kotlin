package agh.softdev.kotlincoroutines

import agh.softdev.kotlincoroutines.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.btnStrat.setOnClickListener{
            CoroutineScope(IO).launch {
                fakeApiRequest();
            }
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResultFromApi();
        setTextOnMainThread(result1);
        println("Debug: $result1");
    }

    // used to set the textview text on the main thread
    // this function create new coroutine that execute on the main thread and switch the the
    // text of the main TextView
    // with suspend keyWord We Can Call This function from author coroutines
    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setResultText(input);
        }
    }

    private fun setResultText(input:String){
        val oldText = binding.result.text;
        binding.result.text = oldText.toString()+"\n${input}";
    }

    private suspend fun getResultFromApi(): String{
        logThread("getResultFromApi");
        delay(1000);
        return "Result #1";
    }

    private fun logThread(methodeName:String){
        println("Debug: ${methodeName} : ${Thread.currentThread().name}");
    }
}
