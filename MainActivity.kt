package com.apexfit.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*

private val Ink = Color(0xFF111216); private val Ivory = Color(0xFFF5F1E8); private val Pearl = Color(0xFFE8E2D6)
private val Gold = Color(0xFFC9A45C); private val Mint = Color(0xFF55C6A5); private val Coral = Color(0xFFE77967); private val Blue = Color(0xFF6EA8FE); private val Violet = Color(0xFF9B8AFB)

private data class Exercise(val name:String,val muscle:String,val equipment:String)
private data class Split(val name:String,val days:Int,val summary:String,val daysText:String)

private val exercises = listOf(
 Exercise("Barbell Bench Press","Chest","Barbell"), Exercise("Incline Dumbbell Press","Chest","Dumbbells"), Exercise("Cable Fly","Chest","Cable"), Exercise("Machine Chest Press","Chest","Machine"), Exercise("Push-Up","Chest","Bodyweight"),
 Exercise("Barbell Row","Back","Barbell"), Exercise("Lat Pulldown","Back","Cable"), Exercise("Pull-Up","Back","Bodyweight"), Exercise("Seated Cable Row","Back","Cable"), Exercise("Single-Arm Dumbbell Row","Back","Dumbbells"),
 Exercise("Barbell Curl","Biceps","Barbell"), Exercise("Incline Dumbbell Curl","Biceps","Dumbbells"), Exercise("Hammer Curl","Biceps","Dumbbells"), Exercise("Preacher Curl","Biceps","Machine"),
 Exercise("Triceps Pushdown","Triceps","Cable"), Exercise("Overhead Triceps Extension","Triceps","Cable"), Exercise("Skull Crusher","Triceps","Barbell"), Exercise("Close-Grip Bench Press","Triceps","Barbell"),
 Exercise("Back Squat","Quads","Barbell"), Exercise("Leg Press","Quads","Machine"), Exercise("Leg Extension","Quads","Machine"), Exercise("Walking Lunge","Quads","Dumbbells"), Exercise("Romanian Deadlift","Hamstrings","Barbell"), Exercise("Leg Curl","Hamstrings","Machine"), Exercise("Calf Raise","Calves","Machine"),
 Exercise("Overhead Press","Shoulders","Barbell"), Exercise("Dumbbell Lateral Raise","Shoulders","Dumbbells"), Exercise("Rear-Delt Fly","Shoulders","Machine")
)
private val splits = listOf(
 Split("Full Body",3,"Train the whole body each session; efficient and recovery-friendly.","Full Body • Rest • Full Body • Rest • Full Body"),
 Split("Upper / Lower",4,"Alternate upper and lower body; excellent for four training days.","Upper • Lower • Rest • Upper • Lower"),
 Split("Push / Pull / Legs",6,"Movement-based split: push, pull and legs, repeated across the week.","Push • Pull • Legs • Rest • Push • Pull • Legs"),
 Split("PPL + Upper / Lower",5,"Hybrid five-day structure for people who want variety and frequency.","Push • Pull • Legs • Upper • Lower"),
 Split("Body-Part Split",5,"Classic bodybuilding structure focusing on one or two muscle groups per session.","Chest • Back • Shoulders • Arms • Legs"),
 Split("Arnold Split",6,"Classic antagonist pairing with chest/back, shoulders/arms and legs.","Chest+Back • Shoulders+Arms • Legs • repeat"),
 Split("Chest + Biceps / Back + Triceps / Legs + Shoulders",3,"Traditional paired-muscle split matching the example you described.","Chest+Biceps • Back+Triceps • Legs+Shoulders")
)

class MainActivity: ComponentActivity(){ override fun onCreate(b:Bundle?){super.onCreate(b);setContent{ApexFit()}} }

@Composable fun ApexFit(){
 MaterialTheme(colorScheme=darkColorScheme(primary=Gold,background=Ink,surface=Color(0xFF191A1F),onBackground=Ivory,onSurface=Ivory)){
  val nav=rememberNavController(); NavHost(nav,"home"){
   composable("home"){Home(nav)}; composable("workout"){Workout(nav)}; composable("split"){SplitChooser(nav)}; composable("builder/{idx}"){Builder(nav,it.arguments?.getString("idx")?.toIntOrNull()?:0)}; composable("nutrition"){Nutrition(nav)}; composable("progress"){Progress(nav)}; composable("profile"){Profile(nav)}
  }
 }
}

@Composable fun Shell(title:String,nav:NavHostController,content:@Composable ColumnScope.()->Unit){
 Scaffold(containerColor=Ink,bottomBar={NavigationBar(containerColor=Color(0xFF15161A)){ listOf("home" to Icons.Default.Home,"workout" to Icons.Default.FitnessCenter,"nutrition" to Icons.Default.Restaurant,"progress" to Icons.Default.ShowChart,"profile" to Icons.Default.Person).forEach{(r,i)->NavigationBarItem(selected=false,onClick={nav.navigate(r){launchSingleTop=true}},icon={Icon(i,null)},label={Text(r.replaceFirstChar{it.uppercase()})})}}}){p->Column(Modifier.padding(p).fillMaxSize()){Row(Modifier.fillMaxWidth().padding(20.dp),verticalAlignment=Alignment.CenterVertically){Text(title,style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Spacer(Modifier.weight(1f));Icon(Icons.Default.Notifications,null,tint=Gold)};content()}}
}
@Composable fun Panel(mod:Modifier=Modifier,content:@Composable ColumnScope.()->Unit){Column(mod.background(Color(0xFF1A1B20),RoundedCornerShape(26.dp)).border(1.dp,Color.White.copy(.07f),RoundedCornerShape(26.dp)).padding(18.dp),content=content)}
@Composable fun Metric(label:String,value:String,accent:Color){Column(Modifier.width(112.dp)){Text(label.uppercase(),fontSize=10.sp,color=Color.White.copy(.55f),fontWeight=FontWeight.Bold);Text(value,fontSize=24.sp,fontWeight=FontWeight.Bold,color=accent)}}

@Composable fun Home(nav:NavHostController)=Shell("Good afternoon",nav){
 LazyColumn(contentPadding=PaddingValues(horizontal=20.dp,vertical=4.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
  item{Text("YOUR TRAINING, YOUR WAY",fontSize=11.sp,color=Gold,fontWeight=FontWeight.Bold);Text("Tuesday • 06 October",fontSize=15.sp,color=Color.White.copy(.55f))}
  item{Panel{Text("TODAY'S READINESS",fontSize=11.sp,color=Mint,fontWeight=FontWeight.Bold);Row(verticalAlignment=Alignment.Bottom){Text("87",fontSize=56.sp,fontWeight=FontWeight.Black);Text(" / 100",fontSize=18.sp,color=Color.White.copy(.5f),modifier=Modifier.padding(bottom=10.dp))};Text("Strong day for a hard session",color=Color.White.copy(.72f));Spacer(Modifier.height(12.dp));LinearProgressIndicator({0.87f},Modifier.fillMaxWidth(),color=Mint,trackColor=Color.White.copy(.08f))}}
  item{Row(horizontalArrangement=Arrangement.spacedBy(12.dp)){Panel(Modifier.weight(1f)){Metric("Calories","1,840",Coral);Text("/ 2,700 kcal",fontSize=11.sp,color=Color.White.copy(.5f))};Panel(Modifier.weight(1f)){Metric("Protein","94 g",Blue);Text("/ 120 g",fontSize=11.sp,color=Color.White.copy(.5f))}}}
  item{Button(onClick={nav.navigate("workout")},modifier=Modifier.fillMaxWidth().height(58.dp),shape=RoundedCornerShape(18.dp)){Icon(Icons.Default.PlayArrow,null);Spacer(Modifier.width(8.dp));Text("START TODAY'S WORKOUT",fontWeight=FontWeight.Bold)}}
  item{Panel{Text("WEEKLY RHYTHM",fontSize=11.sp,color=Violet,fontWeight=FontWeight.Bold);Text("PPL + Upper / Lower",fontSize=22.sp,fontWeight=FontWeight.Bold);Spacer(Modifier.height(8.dp));Text("Push  •  Pull  •  Legs  •  Upper  •  Lower",color=Color.White.copy(.65f));Spacer(Modifier.height(14.dp));Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("M","T","W","T","F","S","S").forEachIndexed{i,d->Box(Modifier.size(36.dp).background(if(i<2)Mint else Color.White.copy(.06f),RoundedCornerShape(12.dp)),Alignment.Center){Text(d,fontWeight=FontWeight.Bold,color=if(i<2)Ink else Ivory)}}}}}
 }
}

@Composable fun Workout(nav:NavHostController)=Shell("Workout Studio",nav){
 Column(Modifier.padding(horizontal=20.dp).verticalScroll(rememberScrollState()),verticalArrangement=Arrangement.spacedBy(14.dp)){
  Panel{Text("BUILD YOUR SPLIT",fontSize=11.sp,color=Gold,fontWeight=FontWeight.Bold);Text("Don't know where to start?",fontSize=24.sp,fontWeight=FontWeight.Bold);Text("Choose a structure first. APEXFIT then lets you choose the exact exercises for every day.",color=Color.White.copy(.65f));Spacer(Modifier.height(12.dp));Button(onClick={nav.navigate("split")}){Text("EXPLORE SPLITS")}}
  Text("YOUR 10 WORKOUT SLOTS",fontSize=12.sp,color=Color.White.copy(.55f),fontWeight=FontWeight.Bold)
  repeat(10){i->Panel(Modifier.fillMaxWidth()){Row(verticalAlignment=Alignment.CenterVertically){Text("${(i+1).toString().padStart(2,'0')}",fontSize=22.sp,color=Gold,fontWeight=FontWeight.Bold);Spacer(Modifier.width(14.dp));Column(Modifier.weight(1f)){Text(if(i==0)"Push A" else "Custom Workout ${i+1}",fontWeight=FontWeight.Bold);Text(if(i==0)"Chest • Shoulders • Triceps" else "Tap to customize",fontSize=12.sp,color=Color.White.copy(.5f))};TextButton(onClick={nav.navigate("builder/$i")}){Text("EDIT")}}}}
 }
}

@Composable fun SplitChooser(nav:NavHostController){
 Column(Modifier.fillMaxSize().background(Ink).padding(20.dp)){Text("Choose your structure",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Text("APEXFIT does not force one ‘best’ split. Pick based on your schedule, experience and preference.",color=Color.White.copy(.62f),modifier=Modifier.padding(top=8.dp,bottom=18.dp));LazyColumn(verticalArrangement=Arrangement.spacedBy(12.dp)){items(splits){s->Panel{Row(verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(s.name,fontSize=20.sp,fontWeight=FontWeight.Bold);Text("${s.days} days / week",color=Gold,fontSize=12.sp,fontWeight=FontWeight.Bold);Spacer(Modifier.height(5.dp));Text(s.summary,color=Color.White.copy(.62f));Text(s.daysText,fontSize=12.sp,color=Mint,modifier=Modifier.padding(top=8.dp))}}Button(onClick={nav.navigate("builder/0")}){Text("USE")}}}}}
}

@Composable fun Builder(nav:NavHostController,idx:Int){
 var selected by remember{mutableStateOf(setOf<String>())}; var query by remember{mutableStateOf("")}; var muscle by remember{mutableStateOf("All")}
 val muscles=listOf("All","Chest","Back","Biceps","Triceps","Shoulders","Quads","Hamstrings","Calves")
 val shown=exercises.filter{(muscle=="All"||it.muscle==muscle)&&(query.isBlank()||it.name.contains(query,true))}
 Column(Modifier.fillMaxSize().background(Ink).padding(20.dp)){Text("Build Workout ${idx+1}",style=MaterialTheme.typography.headlineMedium,fontWeight=FontWeight.Bold);Text("Select the exercises YOU want. Nothing is pre-locked.",color=Color.White.copy(.6f),modifier=Modifier.padding(top=5.dp,bottom=12.dp));OutlinedTextField(query,{query=it},modifier=Modifier.fillMaxWidth(),singleLine=true,label={Text("Search exercise")});Row(Modifier.horizontalScroll(rememberScrollState()).padding(vertical=10.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){muscles.forEach{FilterChip(selected=muscle==it,onClick={muscle=it},label={Text(it)})}};Text("${selected.size} selected",color=Gold,fontWeight=FontWeight.Bold);LazyColumn(Modifier.weight(1f),verticalArrangement=Arrangement.spacedBy(8.dp)){items(shown){e->Row(Modifier.fillMaxWidth().background(Color(0xFF1A1B20),RoundedCornerShape(16.dp)).padding(14.dp),verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(e.name,fontWeight=FontWeight.Bold);Text("${e.muscle} • ${e.equipment}",fontSize=11.sp,color=Color.White.copy(.5f))};Checkbox(selected.contains(e.name),{selected=if(it)selected+e.name else selected-e.name})}}};Button(onClick={nav.navigate("workout")},modifier=Modifier.fillMaxWidth()){Text("SAVE WORKOUT")}}
}

@Composable fun Nutrition(nav:NavHostController)=Shell("Nutrition",nav){Column(Modifier.padding(horizontal=20.dp).verticalScroll(rememberScrollState()),verticalArrangement=Arrangement.spacedBy(14.dp)){Panel{Text("TODAY",fontSize=11.sp,color=Coral,fontWeight=FontWeight.Bold);Text("1,840 kcal",fontSize=42.sp,fontWeight=FontWeight.Black);Text("860 kcal remaining",color=Color.White.copy(.55f));Spacer(Modifier.height(12.dp));LinearProgressIndicator({.68f},Modifier.fillMaxWidth(),color=Coral,trackColor=Color.White.copy(.08f))};Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){Panel(Modifier.weight(1f)){Metric("Protein","94g",Blue)};Panel(Modifier.weight(1f)){Metric("Carbs","211g",Gold)};Panel(Modifier.weight(1f)){Metric("Fats","51g",Violet)}};Button(onClick={},modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.Add,null);Text(" ADD FOOD")};Text("MEALS",fontSize=12.sp,color=Color.White.copy(.5f),fontWeight=FontWeight.Bold);listOf("Breakfast • 520 kcal","Lunch • 640 kcal","Snack • 210 kcal","Dinner • 470 kcal").forEach{Panel{Row{Text(it,fontWeight=FontWeight.Bold);Spacer(Modifier.weight(1f));Text("VIEW",color=Gold,fontSize=11.sp,fontWeight=FontWeight.Bold)}}}}}
@Composable fun Progress(nav:NavHostController)=Shell("Progress",nav){Column(Modifier.padding(horizontal=20.dp).verticalScroll(rememberScrollState()),verticalArrangement=Arrangement.spacedBy(14.dp)){Panel{Text("STRENGTH",fontSize=11.sp,color=Mint,fontWeight=FontWeight.Bold);Text("+18%",fontSize=44.sp,fontWeight=FontWeight.Black);Text("training volume vs. last month",color=Color.White.copy(.55f));Spacer(Modifier.height(18.dp));Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly,verticalAlignment=Alignment.Bottom){listOf(.35f,.52f,.46f,.68f,.61f,.82f,.9f).forEach{Box(Modifier.width(22.dp).height((120*it).dp).background(Mint.copy(.75f),RoundedCornerShape(8.dp)))}}};Panel{Text("PERSONAL RECORDS",fontSize=11.sp,color=Gold,fontWeight=FontWeight.Bold);listOf("Bench Press — 60 kg","Squat — 90 kg","RDL — 70 kg","Pull-Up — 10 reps").forEach{Row(Modifier.fillMaxWidth().padding(vertical=9.dp)){Text(it);Spacer(Modifier.weight(1f));Text("PR",color=Gold,fontWeight=FontWeight.Bold)}}}}
@Composable fun Profile(nav:NavHostController)=Shell("Profile",nav){Column(Modifier.padding(horizontal=20.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){Panel{Text("ATHLETE PROFILE",fontSize=11.sp,color=Gold,fontWeight=FontWeight.Bold);Text("Your Name",fontSize=30.sp,fontWeight=FontWeight.Black);Text("Hypertrophy • 5 days/week",color=Color.White.copy(.6f))};Panel{listOf("Body weight" to "58.5 kg","Height" to "174 cm","Daily calories" to "2,700 kcal","Protein target" to "120 g").forEach{Row(Modifier.fillMaxWidth().padding(vertical=10.dp)){Text(it.first);Spacer(Modifier.weight(1f));Text(it.second,color=Gold,fontWeight=FontWeight.Bold)}}};Text("SETTINGS",fontSize=12.sp,color=Color.White.copy(.5f),fontWeight=FontWeight.Bold);Panel{listOf("Units","Notifications","Theme","Export data").forEach{Row(Modifier.fillMaxWidth().padding(vertical=9.dp)){Text(it);Spacer(Modifier.weight(1f));Icon(Icons.Default.ChevronRight,null,tint=Color.White.copy(.4f))}}}}
