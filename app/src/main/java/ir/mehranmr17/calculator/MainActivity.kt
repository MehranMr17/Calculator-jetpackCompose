package ir.mehranmr17.calculator

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.mehranmr17.calculator.ui.theme.CalculatorTheme
import org.jetbrains.annotations.NotNull


class MainActivity : ComponentActivity() {

    var equation = mutableStateOf("")
    var result = mutableStateOf(0.0)
    val operators = listOf("-", "*", "/", "(", "+", ".")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        window.statusBarColor = Color.Black.toArgb()

        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MainView()
                    }
                }
            }
        }
    }


    @Composable
    fun MainView() {

        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )

        systemUiController.setStatusBarColor(
            color = Color.Black
        )



        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Bottom
        )
        {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                Text(
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .background(Color.Black)
                        .padding(30.dp),
                    letterSpacing = 2.sp,
                    fontSize = 25.sp,
                    text = equation.value,
                    color = Color(0xFFD8D8D8)
                )
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
                Text(
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .background(Color.Black)
                        .padding(30.dp),
                    maxLines = 1,
                    letterSpacing = 2.sp,
                    fontSize = 30.sp,
                    text = (if (result.value == 0.0) {
                        ""
                    } else {
                        var str = result.value.toString()
                        if (result.value.toString().endsWith(".0")) {
                            str = str.substring(0, result.value.toString().length - 2)
                            Log.i("TAG", "getTheResult: $str")
                        }
                        str
                    }),
                    color = Color(0xFFD8D8D8)
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(10.dp, 0.dp),
                    maxLines = 1,
                    letterSpacing = 5.sp,
                    text = "---------------------------------------------------------------------",
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .background(color = Color.Black),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(.25f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    MyButton(sign = "C", name = "Clear", isNumber = false)
                    MyButton(sign = "7", name = "Seven")
                    MyButton(sign = "4", name = "four")
                    MyButton(sign = "1", name = "one")
                    Specialisations(sign = "", name = "Undo")
                }
                Column(
                    modifier = Modifier.fillMaxWidth(.33f),
                    verticalArrangement = Arrangement.Center
                ) {
                    MyButton(sign = "(", name = "OpenParentheses", isNumber = false)
                    MyButton(sign = "8", name = "Eight")
                    MyButton(sign = "5", name = "Five")
                    MyButton(sign = "2", name = "Two")
                    MyButton(sign = "0", name = "Zero")
                }
                Column(
                    modifier = Modifier.fillMaxWidth(.5f),
                    verticalArrangement = Arrangement.Center
                ) {
                    MyButton(sign = ")", name = "CloseParentheses", isNumber = false)
                    MyButton(sign = "9", name = "Nine")
                    MyButton(sign = "6", name = "Six")
                    MyButton(sign = "3", name = "Three")
                    MyButton(sign = ".", name = "Dot", isNumber = false)
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    MyButton(sign = "/", name = "Division", isNumber = false)
                    MyButton(sign = "*", name = "Multiply", isNumber = false)
                    MyButton(sign = "-", name = "Differentiation", isNumber = false)
                    MyButton(sign = "+", name = "Sum", isNumber = false)
                    Specialisations(sign = "=", name = "Equal")
                }
            }
        }
    }


    @Composable
    fun MyButton(sign: String, name: String, isNumber: Boolean = true) {
        val context = LocalContext.current

        if (isNumber || name == "Dot") {
            Button(
                onClick = {
                    if (result.value != 0.0) {
                        equation.value = ""
                        result.value = 0.0
                    }

                    if (equation.value.isNotEmpty() && name == "Dot") {
                        if (operators.contains<String>(equation.value.last().toString())) {
                            equation.value = equation.value + "0" +  (sign)
                        }
                        equation.value = equation.value + (sign)
                    } else
                        if (equation.value.isEmpty() && name == "Dot") {
                            equation.value = "0$sign"
                        } else {
                            equation.value = equation.value + (sign)
                        }

                },
                modifier = Modifier
                    .height(75.dp)
                    .padding(3.dp),
                contentPadding = PaddingValues(15.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,

                    ),

                )
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = sign,
                        color = Color.White,
                        fontSize = 30.sp,
                    )
                }

            }
        } else {
            Button(

                onClick = {

                    if (result.value != 0.0) {
                        var str = result.value.toString()
                        if (result.value.toString().endsWith(".0")) {
                            str = str.substring(0, result.value.toString().length - 2)
                            Log.i("TAG", "getTheResult: $str")
                        }
                        equation.value = str
                        result.value = 0.0
                    }

                    if (name == "Clear") {
                        equation.value = ""
                        result.value = 0.0
                        return@Button
                    }
                    var containsOperator = false
                    operators.forEach {
                        if (equation.value.contains(it)) {
                            containsOperator = true
                        }
                    }
                    if (equation.value.isNotEmpty() && !containsOperator) {


                        if (operators.contains<String>(equation.value.last().toString())) {
                            equation.value =
                                equation.value.substring(0, equation.value.length - 1)
                        }
                        equation.value = equation.value + (sign)
                        Log.i("TAG", "MyButton: ${equation.value}")

                    } else
                        if (equation.value.isNotEmpty() && operators.contains<String>(
                                equation.value.last().toString()
                            )
                        ) {
                            equation.value = equation.value.substring(0, equation.value.length - 1)
                            equation.value = equation.value + (sign)
                        }else if(equation.value.isNotEmpty() && containsOperator){
                            getTheResult()
                            var str = result.value.toString()
                            if (result.value.toString().endsWith(".0")) {
                                str = str.substring(0, result.value.toString().length - 2)
                                Log.i("TAG", "getTheResult: $str")
                            }
                            equation.value = str
                            result.value = 0.0
                            equation.value = equation.value + (sign)
                        }
                },
                modifier = Modifier
                    .height(75.dp)
                    .padding(3.dp),
                contentPadding = PaddingValues(15.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                ),

                )
            {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = sign,
                        color = Color(0xFFFE5925),
                        fontSize = 30.sp,
                    )
                }
            }


        }
    }

    @Composable
    fun Specialisations(sign: String, name: String) {
        if (name == "Equal") {
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { getTheResult() },
                    modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)
                        .padding(3.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFE5925),
                        contentColor = Color.Black
                    ),

                    shape = RoundedCornerShape(100.dp)


                )
                {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                    }

                }
            }
        } else if (name == "Undo") {
            Box(
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (equation.value.length > 0) {
                            equation.value = equation.value.substring(0, equation.value.length - 1)
                            result.value = 0.0
                        }
                    },
                    modifier = Modifier
                        .height(75.dp)
                        .padding(3.dp),

                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color(0xFFFE5925),
                    )
                )
                {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(R.drawable.undo_icon),
                            contentDescription = ""
                        )
                    }

                }
            }
        }
    }

    private fun getTheResult() {
        if (equation.value.isNotEmpty() && !operators.contains<String>(
                equation.value.last().toString()
            )
        ) {

            val operatorsCount = mutableMapOf<String, Int>()
            operators.forEach { operator ->
                operatorsCount[operator] = equation.value.filter {
                    it.toString() == operator
                }.length
            }

            if (equation.value.contains("/")) {
                val number1 = equation.value.substring(0, equation.value.indexOfFirst {
                    it.toString() == "/"
                }
                )

                val number2 = equation.value.substring(
                    equation.value.indexOfFirst {
                        it.toString() == "/"
                    } + 1, equation.value.length
                )

                result.value = (number1.toDouble()) / (number2.toDouble())
            }
            if (equation.value.contains("*")) {
                val number1 = equation.value.substring(0, equation.value.indexOfFirst {
                    it.toString() == "*"
                }
                )

                val number2 = equation.value.substring(
                    equation.value.indexOfFirst {
                        it.toString() == "*"
                    } + 1, equation.value.length
                )

                result.value = (number1.toDouble()) * (number2.toDouble())
            }
            if (equation.value.contains("-")) {
                val number1 = equation.value.substring(0, equation.value.indexOfFirst {
                    it.toString() == "-"
                }
                )

                val number2 = equation.value.substring(
                    equation.value.indexOfFirst {
                        it.toString() == "-"
                    } + 1, equation.value.length
                )

                result.value = (number1.toDouble()) - (number2.toDouble())
            }
            if (equation.value.contains("+")) {
                val number1 = equation.value.substring(0, equation.value.indexOfFirst {
                    it.toString() == "+"
                }
                )

                val number2 = equation.value.substring(
                    equation.value.indexOfFirst {
                        it.toString() == "+"
                    } + 1, equation.value.length
                )

                result.value = (number1.toDouble()) + (number2.toDouble())
            }


        }

    }


    @Composable
    @Preview(name = "First project", showBackground = true)
    fun pre() {
        MainView()
    }

}