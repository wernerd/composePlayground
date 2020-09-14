package com.example.playground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.ConstraintSet
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.atMost
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.playground.ui.PlaygroundTheme
import com.example.playground.ui.purple200
import com.example.playground.ui.purple500
import com.example.playground.ui.purple700
import com.example.playground.ui.teal200

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //                   Greeting("Android")
                    //                  constraintInlineDsl()
                    //                  constraintTest()
                    fourButtonsCLWithDSL(emptyList())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var counter by remember { mutableStateOf(1) }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = counter.toString(), modifier = Modifier.offset(y = 50.dp).padding(start = 16.dp).clickable(onClick = {
            counter++
        }))
    }
}


@Composable
fun TextWithPos(content: String, modifier: Modifier, x: Int, y: Int, clickFun: () -> Unit = {}) {

    Box(modifier = Modifier.fillMaxSize()
            .background(
                    LinearGradient(
                            0.0f to Color.Red,
                            0.5f to Color.Green,
                            1.0f to Color.Blue,
                            startX = 0.0f,
                            startY = with(DensityAmbient.current) { 0.0.dp.toPx() },
                            endX = with(DensityAmbient.current) { 100.0.dp.toPx() },
                            endY = with(DensityAmbient.current) { 100.0.dp.toPx() }
                    )
            )
    ) {
        Text(text = content, modifier = modifier
                .defaultMinSizeConstraints(minHeight = y.dp)
                .clickable(onClick = clickFun)
                .padding(top = y.dp))
    }

}

// This link: https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/package-summary.html#constraintlayout
// also shows an inline constraint layout definition
@Composable
fun constraintTest() {
    ConstraintLayout(constraintSet = ConstraintSet {

        val textA = createRefFor("TextViewA")
        val textB = createRefFor("TextViewB")
        val textC = createRefFor("TextViewC")
        val textD = createRefFor("TextViewD")
        val textE = createRefFor("TextViewE")

        // This block contains all the constraints related to children
        constrain(textA) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        constrain(textB) {
            end.linkTo(textA.start)
            top.linkTo(textA.top)
        }
        constrain(textC) {
            start.linkTo(textA.end)
            top.linkTo(textA.top)
        }
        constrain(textD) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            bottom.linkTo(textA.top)
        }
        constrain(textE) {
            end.linkTo(parent.end)
            start.linkTo(parent.start)
            top.linkTo(textA.bottom)
        }

    }, modifier = Modifier.fillMaxSize()
    ) {

        // This block contains the children
        Text(text = "A", modifier = Modifier.layoutId("TextViewA")
            .padding(10.dp), style = TextStyle(fontSize = 20.sp)
        )
        Text(text = "B", modifier = Modifier.layoutId("TextViewB")
            .padding(10.dp), style = TextStyle(fontSize = 20.sp)
        )
        Text(text = "C", modifier = Modifier.layoutId("TextViewC")
            .padding(10.dp), style = TextStyle(fontSize = 20.sp)
        )
        Text(text = "D", modifier = Modifier.layoutId("TextViewD"), style = TextStyle(fontSize = 20.sp))
        Text(text = "E", modifier = Modifier.layoutId("TextViewE"), style = TextStyle(fontSize = 20.sp))

    }
}

object FourElementsNoDSL {
    const val elementA = "ElementA"
    const val elementB = "ElementB"
    const val elementC = "ElementC"
    const val elementD = "ElementD"

    private val noDSLConstraintSet = ConstraintSet {

        // Create references with defines ids, here using a string as id. Could be an Int as well,
        // actually it's defined as 'Any'
        val elemA = createRefFor(elementA)
        val elemB = createRefFor(elementB)
        val elemC = createRefFor(elementC)
        val elemD = createRefFor(elementD)

        // Simple chain only. Instead of this simple chain we can use (for example):
        //     constrain(elemA) {start.linkTo(parent.start) }
        // to set a constraint as known in XML

//        constrain(elemA) {start.linkTo(parent.start, 16.dp) }
//        constrain(elemB) {start.linkTo(elemA.end) }
//        constrain(elemC) {start.linkTo(elemB.end) }
//        constrain(elemD) {end.linkTo(parent.end) }
        createHorizontalChain(elemA, elemB, elemC, elemD)
    }

    @Composable
    fun fourButtonsCLNoDSL(doNotShow: List<String>) {
        ConstraintLayout(constraintSet = noDSLConstraintSet, modifier = Modifier.fillMaxSize()) {

            // This block contains the children
            Text(text = "A",
                    modifier = Modifier.layoutId(elementA)
                            .drawOpacity(if (doNotShow.contains(elementA)) 0f else 1f)
                            .padding(0.dp),
                    style = TextStyle(fontSize = 20.sp)
            )
            Text(text = "B",
                    modifier = Modifier.layoutId(elementB)
                            .drawOpacity(if (doNotShow.contains(elementB)) 0f else 1f)
                            .padding(0.dp),
                    style = TextStyle(fontSize = 20.sp)
            )
            Text(text = "C",
                    modifier = Modifier.layoutId(elementC)
                            .drawOpacity(if (doNotShow.contains(elementC)) 0f else 1f)
                            .padding(0.dp),
                    style = TextStyle(fontSize = 20.sp)
            )
            Text(text = "D",
                    modifier = Modifier.layoutId(elementD)
                            .drawOpacity(if (doNotShow.contains(elementD)) 0f else 1f)
                            .padding(0.dp),
                    style = TextStyle(fontSize = 20.sp))

        }
    }
}

//data class MyText(var textData: String): ReadOnlyProperty<Nothing?, String> {
//    override  fun getValue(thisRef: Nothing?, property: KProperty<*>): String = textData
//    operator fun setValue(thisRef: Nothing?, property: KProperty<*>, value: String) {
//        textData = value
//    }
//}

@Composable
fun fourButtonsCLWithDSL(doNotShow: List<String>) {

    ConstraintLayout(Modifier.fillMaxSize()) {

        val (btn1, btn2, btn3, btn4) = createRefs()

        TextButton(onClick = {}, Modifier.constrainAs(btn1) {}.background(teal200)) { Text("Button1") }
        TextButton(onClick = {}, Modifier.constrainAs(btn2) {}.background(teal200)) { Text("Button2") }
        TextButton(onClick = {}, Modifier.constrainAs(btn3) {}.background(teal200)) { Text("Button3") }
        TextButton(onClick = {}, Modifier.constrainAs(btn4) {}.background(teal200)) { Text("Button4") }

        createHorizontalChain(btn1, btn2, btn3, btn4)
    }
}

@Composable
fun fourButtonsRow() {
    var textData by remember { mutableStateOf("Button1") }
    var upperLower = true

    Row(Modifier.fillMaxWidth()) {
        TextButton(onClick = {
            textData = if (upperLower) "BUTTON1" else "Button1"
            upperLower =!upperLower
        },
            Modifier.drawOpacity(1f).padding(8.dp).background(teal200).padding(4.dp).background(purple500)) {
            Text(text = textData, Modifier.background(purple700).padding(8.dp).background(purple200))
        }
        TextButton(onClick = {}) { Text("Button2") }
        TextButton(onClick = {}) { Text("Button3") }
        TextButton(onClick = {}) { Text("Button4") }

    }
}

@Composable
fun constraintInlineDsl() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (text1, text2, text3) = createRefs()

        var showText1 by mutableStateOf(true)
        Text("Text1", Modifier.constrainAs(text1) {
            if (showText1) start.linkTo(parent.start, margin = 20.dp) else end.linkTo(parent.end, margin = 20.dp)
        })

        var count by remember { mutableStateOf(100_000) }

        // content that you want to make clickable
        Text(text = count.toString(),
            modifier = Modifier
                .clickable {
                    Log.e("ConstraintDSL", "++++ click")
                    count += 1 // += 100_000 forces Text to expand, adding just 1 works, Text does not expand
                    showText1 = !showText1
                }
                .constrainAs(text2) {
                    centerTo(parent)
                })

        val barrier = createBottomBarrier(text1, text2)
        Text("This is a very long text", Modifier.constrainAs(text3) {
            top.linkTo(barrier, margin = 20.dp)
            centerHorizontallyTo(parent)
            width = Dimension.preferredWrapContent.atMost(40.dp)
        })

    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    PlaygroundTheme {
//        Greeting("Android")
//    }
//}

@Preview
@Composable
fun TextPosPreview() {
    PlaygroundTheme {
            Column() {
                var counter by remember { mutableStateOf(1) }
                TextWithPos(content = "Hello", modifier = Modifier.padding(start = 16.dp, end = 16.dp), x = 0, y = 50) {
                    counter++
                }
                Text(text = counter.toString(), modifier = Modifier
                        .padding(16.dp))

            }
        }
}

@Preview(showBackground = true)
@Composable
fun previewFourButtonsDSL() {
    PlaygroundTheme {
        fourButtonsCLWithDSL(emptyList())
    }
}

@Preview(showBackground = true)
@Composable
fun previewFourFieldsNoDSL() {
    val noShow = listOf(FourElementsNoDSL.elementC)
    PlaygroundTheme {
        FourElementsNoDSL.fourButtonsCLNoDSL(noShow)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun previewFourButtonsRow() {
//    PlaygroundTheme {
//        fourButtonsRow()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun ConstraintPreview() {
//    PlaygroundTheme {
//        constraintTest()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ConstraintDSL() {
//    PlaygroundTheme {
//        constraintInlineDsl()
//    }
//}
//
//@Composable
//@Preview
//fun TestPreview() {
//    PlaygroundTheme {
//        Greeting("Testing")
//    }
//}
