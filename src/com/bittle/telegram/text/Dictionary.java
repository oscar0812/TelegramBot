package com.bittle.telegram.text;

import com.bittle.telegram.WebCrawler;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by oscartorres on 6/28/17.
 */
public class Dictionary {
    private ArrayList<String> word_list = new ArrayList<>();

    public Dictionary() {
        word_list.add("the");
        word_list.add("and");
        word_list.add("you");
        word_list.add("that");
        word_list.add("was");
        word_list.add("for");
        word_list.add("are");
        word_list.add("with");
        word_list.add("his");
        word_list.add("they");
        word_list.add("one");
        word_list.add("have");
        word_list.add("this");
        word_list.add("from");
        word_list.add("had");
        word_list.add("hot");
        word_list.add("word");
        word_list.add("but");
        word_list.add("what");
        word_list.add("some");
        word_list.add("can");
        word_list.add("out");
        word_list.add("other");
        word_list.add("were");
        word_list.add("all");
        word_list.add("there");
        word_list.add("when");
        word_list.add("use");
        word_list.add("your");
        word_list.add("how");
        word_list.add("said");
        word_list.add("each");
        word_list.add("she");
        word_list.add("which");
        word_list.add("their");
        word_list.add("time");
        word_list.add("will");
        word_list.add("way");
        word_list.add("about");
        word_list.add("many");
        word_list.add("then");
        word_list.add("them");
        word_list.add("write");
        word_list.add("would");
        word_list.add("like");
        word_list.add("these");
        word_list.add("her");
        word_list.add("long");
        word_list.add("make");
        word_list.add("thing");
        word_list.add("see");
        word_list.add("him");
        word_list.add("two");
        word_list.add("has");
        word_list.add("look");
        word_list.add("more");
        word_list.add("day");
        word_list.add("could");
        word_list.add("come");
        word_list.add("did");
        word_list.add("number");
        word_list.add("sound");
        word_list.add("most");
        word_list.add("people");
        word_list.add("over");
        word_list.add("know");
        word_list.add("water");
        word_list.add("than");
        word_list.add("call");
        word_list.add("first");
        word_list.add("who");
        word_list.add("may");
        word_list.add("down");
        word_list.add("side");
        word_list.add("been");
        word_list.add("now");
        word_list.add("find");
        word_list.add("any");
        word_list.add("new");
        word_list.add("work");
        word_list.add("part");
        word_list.add("take");
        word_list.add("get");
        word_list.add("place");
        word_list.add("made");
        word_list.add("live");
        word_list.add("where");
        word_list.add("after");
        word_list.add("back");
        word_list.add("little");
        word_list.add("only");
        word_list.add("round");
        word_list.add("man");
        word_list.add("year");
        word_list.add("came");
        word_list.add("show");
        word_list.add("every");
        word_list.add("good");
        word_list.add("give");
        word_list.add("our");
        word_list.add("under");
        word_list.add("name");
        word_list.add("very");
        word_list.add("through");
        word_list.add("just");
        word_list.add("form");
        word_list.add("sentence");
        word_list.add("great");
        word_list.add("think");
        word_list.add("say");
        word_list.add("help");
        word_list.add("low");
        word_list.add("line");
        word_list.add("differ");
        word_list.add("turn");
        word_list.add("cause");
        word_list.add("much");
        word_list.add("mean");
        word_list.add("before");
        word_list.add("move");
        word_list.add("right");
        word_list.add("boy");
        word_list.add("old");
        word_list.add("too");
        word_list.add("same");
        word_list.add("tell");
        word_list.add("does");
        word_list.add("set");
        word_list.add("three");
        word_list.add("want");
        word_list.add("air");
        word_list.add("well");
        word_list.add("also");
        word_list.add("play");
        word_list.add("small");
        word_list.add("end");
        word_list.add("put");
        word_list.add("home");
        word_list.add("read");
        word_list.add("hand");
        word_list.add("port");
        word_list.add("large");
        word_list.add("spell");
        word_list.add("add");
        word_list.add("even");
        word_list.add("land");
        word_list.add("here");
        word_list.add("must");
        word_list.add("big");
        word_list.add("high");
        word_list.add("such");
        word_list.add("follow");
        word_list.add("act");
        word_list.add("why");
        word_list.add("ask");
        word_list.add("men");
        word_list.add("change");
        word_list.add("went");
        word_list.add("light");
        word_list.add("kind");
        word_list.add("off");
        word_list.add("need");
        word_list.add("house");
        word_list.add("picture");
        word_list.add("try");
        word_list.add("again");
        word_list.add("animal");
        word_list.add("point");
        word_list.add("mother");
        word_list.add("world");
        word_list.add("near");
        word_list.add("build");
        word_list.add("self");
        word_list.add("earth");
        word_list.add("father");
        word_list.add("head");
        word_list.add("stand");
        word_list.add("own");
        word_list.add("page");
        word_list.add("should");
        word_list.add("country");
        word_list.add("found");
        word_list.add("answer");
        word_list.add("school");
        word_list.add("grow");
        word_list.add("study");
        word_list.add("still");
        word_list.add("learn");
        word_list.add("plant");
        word_list.add("cover");
        word_list.add("food");
        word_list.add("sun");
        word_list.add("four");
        word_list.add("between");
        word_list.add("state");
        word_list.add("keep");
        word_list.add("eye");
        word_list.add("never");
        word_list.add("last");
        word_list.add("let");
        word_list.add("thought");
        word_list.add("city");
        word_list.add("tree");
        word_list.add("cross");
        word_list.add("farm");
        word_list.add("hard");
        word_list.add("start");
        word_list.add("might");
        word_list.add("story");
        word_list.add("saw");
        word_list.add("far");
        word_list.add("sea");
        word_list.add("draw");
        word_list.add("left");
        word_list.add("late");
        word_list.add("run");
        word_list.add("don't");
        word_list.add("while");
        word_list.add("press");
        word_list.add("close");
        word_list.add("night");
        word_list.add("real");
        word_list.add("life");
        word_list.add("few");
        word_list.add("north");
        word_list.add("open");
        word_list.add("seem");
        word_list.add("together");
        word_list.add("next");
        word_list.add("white");
        word_list.add("children");
        word_list.add("begin");
        word_list.add("got");
        word_list.add("walk");
        word_list.add("example");
        word_list.add("ease");
        word_list.add("paper");
        word_list.add("group");
        word_list.add("always");
        word_list.add("music");
        word_list.add("those");
        word_list.add("both");
        word_list.add("mark");
        word_list.add("often");
        word_list.add("letter");
        word_list.add("until");
        word_list.add("mile");
        word_list.add("river");
        word_list.add("car");
        word_list.add("feet");
        word_list.add("care");
        word_list.add("second");
        word_list.add("book");
        word_list.add("carry");
        word_list.add("took");
        word_list.add("science");
        word_list.add("eat");
        word_list.add("room");
        word_list.add("friend");
        word_list.add("began");
        word_list.add("idea");
        word_list.add("fish");
        word_list.add("mountain");
        word_list.add("stop");
        word_list.add("once");
        word_list.add("base");
        word_list.add("hear");
        word_list.add("horse");
        word_list.add("cut");
        word_list.add("sure");
        word_list.add("watch");
        word_list.add("color");
        word_list.add("face");
        word_list.add("wood");
        word_list.add("main");
        word_list.add("enough");
        word_list.add("plain");
        word_list.add("girl");
        word_list.add("usual");
        word_list.add("young");
        word_list.add("ready");
        word_list.add("above");
        word_list.add("ever");
        word_list.add("red");
        word_list.add("list");
        word_list.add("though");
        word_list.add("feel");
        word_list.add("talk");
        word_list.add("bird");
        word_list.add("soon");
        word_list.add("body");
        word_list.add("dog");
        word_list.add("family");
        word_list.add("direct");
        word_list.add("pose");
        word_list.add("leave");
        word_list.add("song");
        word_list.add("measure");
        word_list.add("door");
        word_list.add("product");
        word_list.add("black");
        word_list.add("short");
        word_list.add("numeral");
        word_list.add("class");
        word_list.add("wind");
        word_list.add("question");
        word_list.add("happen");
        word_list.add("complete");
        word_list.add("ship");
        word_list.add("area");
        word_list.add("half");
        word_list.add("rock");
        word_list.add("order");
        word_list.add("fire");
        word_list.add("south");
        word_list.add("problem");
        word_list.add("piece");
        word_list.add("told");
        word_list.add("knew");
        word_list.add("pass");
        word_list.add("since");
        word_list.add("top");
        word_list.add("whole");
        word_list.add("king");
        word_list.add("space");
        word_list.add("heard");
        word_list.add("best");
        word_list.add("hour");
        word_list.add("better");
        word_list.add("true");
        word_list.add("during");
        word_list.add("hundred");
        word_list.add("five");
        word_list.add("remember");
        word_list.add("step");
        word_list.add("early");
        word_list.add("hold");
        word_list.add("west");
        word_list.add("ground");
        word_list.add("interest");
        word_list.add("reach");
        word_list.add("fast");
        word_list.add("verb");
        word_list.add("sing");
        word_list.add("listen");
        word_list.add("six");
        word_list.add("table");
        word_list.add("travel");
        word_list.add("less");
        word_list.add("morning");
        word_list.add("ten");
        word_list.add("simple");
        word_list.add("several");
        word_list.add("vowel");
        word_list.add("toward");
        word_list.add("war");
        word_list.add("lay");
        word_list.add("against");
        word_list.add("pattern");
        word_list.add("slow");
        word_list.add("center");
        word_list.add("love");
        word_list.add("person");
        word_list.add("money");
        word_list.add("serve");
        word_list.add("appear");
        word_list.add("road");
        word_list.add("map");
        word_list.add("rain");
        word_list.add("rule");
        word_list.add("govern");
        word_list.add("pull");
        word_list.add("cold");
        word_list.add("notice");
        word_list.add("voice");
        word_list.add("unit");
        word_list.add("power");
        word_list.add("town");
        word_list.add("fine");
        word_list.add("certain");
        word_list.add("fly");
        word_list.add("fall");
        word_list.add("lead");
        word_list.add("cry");
        word_list.add("dark");
        word_list.add("machine");
        word_list.add("note");
        word_list.add("wait");
        word_list.add("plan");
        word_list.add("figure");
        word_list.add("star");
        word_list.add("box");
        word_list.add("noun");
        word_list.add("field");
        word_list.add("rest");
        word_list.add("correct");
        word_list.add("able");
        word_list.add("pound");
        word_list.add("done");
        word_list.add("beauty");
        word_list.add("drive");
        word_list.add("stood");
        word_list.add("contain");
        word_list.add("front");
        word_list.add("teach");
        word_list.add("week");
        word_list.add("final");
        word_list.add("gave");
        word_list.add("green");
        word_list.add("quick");
        word_list.add("develop");
        word_list.add("ocean");
        word_list.add("warm");
        word_list.add("free");
        word_list.add("minute");
        word_list.add("strong");
        word_list.add("special");
        word_list.add("mind");
        word_list.add("behind");
        word_list.add("clear");
        word_list.add("tail");
        word_list.add("produce");
        word_list.add("fact");
        word_list.add("street");
        word_list.add("inch");
        word_list.add("multiply");
        word_list.add("nothing");
        word_list.add("course");
        word_list.add("stay");
        word_list.add("wheel");
        word_list.add("full");
        word_list.add("force");
        word_list.add("blue");
        word_list.add("object");
        word_list.add("decide");
        word_list.add("surface");
        word_list.add("deep");
        word_list.add("moon");
        word_list.add("island");
        word_list.add("foot");
        word_list.add("system");
        word_list.add("busy");
        word_list.add("test");
        word_list.add("record");
        word_list.add("boat");
        word_list.add("common");
        word_list.add("gold");
        word_list.add("possible");
        word_list.add("plane");
        word_list.add("stead");
        word_list.add("dry");
        word_list.add("wonder");
        word_list.add("laugh");
        word_list.add("thousand");
        word_list.add("ago");
        word_list.add("ran");
        word_list.add("check");
        word_list.add("game");
        word_list.add("shape");
        word_list.add("equate");
        word_list.add("hot");
        word_list.add("miss");
        word_list.add("brought");
        word_list.add("heat");
        word_list.add("snow");
        word_list.add("tire");
        word_list.add("bring");
        word_list.add("yes");
        word_list.add("distant");
        word_list.add("fill");
        word_list.add("east");
        word_list.add("paint");
        word_list.add("language");
        word_list.add("among");
        word_list.add("grand");
        word_list.add("ball");
        word_list.add("yet");
        word_list.add("wave");
        word_list.add("drop");
        word_list.add("heart");
        word_list.add("present");
        word_list.add("heavy");
        word_list.add("dance");
        word_list.add("engine");
        word_list.add("position");
        word_list.add("arm");
        word_list.add("wide");
        word_list.add("sail");
        word_list.add("material");
        word_list.add("size");
        word_list.add("vary");
        word_list.add("settle");
        word_list.add("speak");
        word_list.add("weight");
        word_list.add("general");
        word_list.add("ice");
        word_list.add("matter");
        word_list.add("circle");
        word_list.add("pair");
        word_list.add("include");
        word_list.add("divide");
        word_list.add("syllable");
        word_list.add("felt");
        word_list.add("perhaps");
        word_list.add("pick");
        word_list.add("sudden");
        word_list.add("count");
        word_list.add("square");
        word_list.add("reason");
        word_list.add("length");
        word_list.add("represent");
        word_list.add("art");
        word_list.add("subject");
        word_list.add("region");
        word_list.add("energy");
        word_list.add("hunt");
        word_list.add("probable");
        word_list.add("bed");
        word_list.add("brother");
        word_list.add("egg");
        word_list.add("ride");
        word_list.add("cell");
        word_list.add("believe");
        word_list.add("fraction");
        word_list.add("forest");
        word_list.add("sit");
        word_list.add("race");
        word_list.add("window");
        word_list.add("store");
        word_list.add("summer");
        word_list.add("train");
        word_list.add("sleep");
        word_list.add("prove");
        word_list.add("lone");
        word_list.add("leg");
        word_list.add("exercise");
        word_list.add("wall");
        word_list.add("catch");
        word_list.add("mount");
        word_list.add("wish");
        word_list.add("sky");
        word_list.add("board");
        word_list.add("joy");
        word_list.add("winter");
        word_list.add("sat");
        word_list.add("written");
        word_list.add("wild");
        word_list.add("instrument");
        word_list.add("kept");
        word_list.add("glass");
        word_list.add("grass");
        word_list.add("cow");
        word_list.add("job");
        word_list.add("edge");
        word_list.add("sign");
        word_list.add("visit");
        word_list.add("past");
        word_list.add("soft");
        word_list.add("fun");
        word_list.add("bright");
        word_list.add("gas");
        word_list.add("weather");
        word_list.add("month");
        word_list.add("million");
        word_list.add("bear");
        word_list.add("finish");
        word_list.add("happy");
        word_list.add("hope");
        word_list.add("flower");
        word_list.add("clothe");
        word_list.add("strange");
        word_list.add("gone");
        word_list.add("jump");
        word_list.add("baby");
        word_list.add("eight");
        word_list.add("village");
        word_list.add("meet");
        word_list.add("root");
        word_list.add("buy");
        word_list.add("raise");
        word_list.add("solve");
        word_list.add("metal");
        word_list.add("whether");
        word_list.add("push");
        word_list.add("seven");
        word_list.add("paragraph");
        word_list.add("third");
        word_list.add("shall");
        word_list.add("held");
        word_list.add("hair");
        word_list.add("describe");
        word_list.add("cook");
        word_list.add("floor");
        word_list.add("either");
        word_list.add("result");
        word_list.add("burn");
        word_list.add("hill");
        word_list.add("safe");
        word_list.add("cat");
        word_list.add("century");
        word_list.add("consider");
        word_list.add("type");
        word_list.add("law");
        word_list.add("bit");
        word_list.add("coast");
        word_list.add("copy");
        word_list.add("phrase");
        word_list.add("silent");
        word_list.add("tall");
        word_list.add("sand");
        word_list.add("soil");
        word_list.add("roll");
        word_list.add("temperature");
        word_list.add("finger");
        word_list.add("industry");
        word_list.add("value");
        word_list.add("fight");
        word_list.add("lie");
        word_list.add("beat");
        word_list.add("excite");
        word_list.add("natural");
        word_list.add("view");
        word_list.add("sense");
        word_list.add("ear");
        word_list.add("else");
        word_list.add("quite");
        word_list.add("broke");
        word_list.add("case");
        word_list.add("middle");
        word_list.add("kill");
        word_list.add("son");
        word_list.add("lake");
        word_list.add("moment");
        word_list.add("scale");
        word_list.add("loud");
        word_list.add("spring");
        word_list.add("observe");
        word_list.add("child");
        word_list.add("straight");
        word_list.add("consonant");
        word_list.add("nation");
        word_list.add("dictionary");
        word_list.add("milk");
        word_list.add("speed");
        word_list.add("method");
        word_list.add("organ");
        word_list.add("pay");
        word_list.add("age");
        word_list.add("section");
        word_list.add("dress");
        word_list.add("cloud");
        word_list.add("surprise");
        word_list.add("quiet");
        word_list.add("stone");
        word_list.add("tiny");
        word_list.add("climb");
        word_list.add("cool");
        word_list.add("design");
        word_list.add("poor");
        word_list.add("lot");
        word_list.add("experiment");
        word_list.add("bottom");
        word_list.add("key");
        word_list.add("iron");
        word_list.add("single");
        word_list.add("stick");
        word_list.add("flat");
        word_list.add("twenty");
        word_list.add("skin");
        word_list.add("smile");
        word_list.add("crease");
        word_list.add("hole");
        word_list.add("trade");
        word_list.add("melody");
        word_list.add("trip");
        word_list.add("office");
        word_list.add("receive");
        word_list.add("row");
        word_list.add("mouth");
        word_list.add("exact");
        word_list.add("symbol");
        word_list.add("die");
        word_list.add("least");
        word_list.add("trouble");
        word_list.add("shout");
        word_list.add("except");
        word_list.add("wrote");
        word_list.add("seed");
        word_list.add("tone");
        word_list.add("join");
        word_list.add("suggest");
        word_list.add("clean");
        word_list.add("break");
        word_list.add("lady");
        word_list.add("yard");
        word_list.add("rise");
        word_list.add("bad");
        word_list.add("blow");
        word_list.add("oil");
        word_list.add("blood");
        word_list.add("touch");
        word_list.add("grew");
        word_list.add("cent");
        word_list.add("mix");
        word_list.add("team");
        word_list.add("wire");
        word_list.add("cost");
        word_list.add("lost");
        word_list.add("brown");
        word_list.add("wear");
        word_list.add("garden");
        word_list.add("equal");
        word_list.add("sent");
        word_list.add("choose");
        word_list.add("fell");
        word_list.add("fit");
        word_list.add("flow");
        word_list.add("fair");
        word_list.add("bank");
        word_list.add("collect");
        word_list.add("save");
        word_list.add("control");
        word_list.add("decimal");
        word_list.add("gentle");
        word_list.add("woman");
        word_list.add("captain");
        word_list.add("practice");
        word_list.add("separate");
        word_list.add("difficult");
        word_list.add("doctor");
        word_list.add("please");
        word_list.add("protect");
        word_list.add("noon");
        word_list.add("whose");
        word_list.add("locate");
        word_list.add("ring");
        word_list.add("character");
        word_list.add("insect");
        word_list.add("caught");
        word_list.add("period");
        word_list.add("indicate");
        word_list.add("radio");
        word_list.add("spoke");
        word_list.add("atom");
        word_list.add("human");
        word_list.add("history");
        word_list.add("effect");
        word_list.add("electric");
        word_list.add("expect");
        word_list.add("crop");
        word_list.add("modern");
        word_list.add("element");
        word_list.add("hit");
        word_list.add("student");
        word_list.add("corner");
        word_list.add("party");
        word_list.add("supply");
        word_list.add("bone");
        word_list.add("rail");
        word_list.add("imagine");
        word_list.add("provide");
        word_list.add("agree");
        word_list.add("thus");
        word_list.add("capital");
        word_list.add("won't");
        word_list.add("chair");
        word_list.add("danger");
        word_list.add("fruit");
        word_list.add("rich");
        word_list.add("thick");
        word_list.add("soldier");
        word_list.add("process");
        word_list.add("operate");
        word_list.add("guess");
        word_list.add("necessary");
        word_list.add("sharp");
        word_list.add("wing");
        word_list.add("create");
        word_list.add("neighbor");
        word_list.add("wash");
        word_list.add("bat");
        word_list.add("rather");
        word_list.add("crowd");
        word_list.add("corn");
        word_list.add("compare");
        word_list.add("poem");
        word_list.add("string");
        word_list.add("bell");
        word_list.add("depend");
        word_list.add("meat");
        word_list.add("rub");
        word_list.add("tube");
        word_list.add("famous");
        word_list.add("dollar");
        word_list.add("stream");
        word_list.add("fear");
        word_list.add("sight");
        word_list.add("thin");
        word_list.add("triangle");
        word_list.add("planet");
        word_list.add("hurry");
        word_list.add("chief");
        word_list.add("colony");
        word_list.add("clock");
        word_list.add("mine");
        word_list.add("tie");
        word_list.add("enter");
        word_list.add("major");
        word_list.add("fresh");
        word_list.add("search");
        word_list.add("send");
        word_list.add("yellow");
        word_list.add("gun");
        word_list.add("allow");
        word_list.add("print");
        word_list.add("dead");
        word_list.add("spot");
        word_list.add("desert");
        word_list.add("suit");
        word_list.add("current");
        word_list.add("lift");
        word_list.add("rose");
        word_list.add("continue");
        word_list.add("block");
        word_list.add("chart");
        word_list.add("hat");
        word_list.add("sell");
        word_list.add("success");
        word_list.add("company");
        word_list.add("subtract");
        word_list.add("event");
        word_list.add("particular");
        word_list.add("deal");
        word_list.add("swim");
        word_list.add("term");
        word_list.add("opposite");
        word_list.add("wife");
        word_list.add("shoe");
        word_list.add("shoulder");
        word_list.add("spread");
        word_list.add("arrange");
        word_list.add("camp");
        word_list.add("invent");
        word_list.add("cotton");
        word_list.add("born");
        word_list.add("determine");
        word_list.add("quart");
        word_list.add("nine");
        word_list.add("truck");
        word_list.add("noise");
        word_list.add("level");
        word_list.add("chance");
        word_list.add("gather");
        word_list.add("shop");
        word_list.add("stretch");
        word_list.add("throw");
        word_list.add("shine");
        word_list.add("property");
        word_list.add("column");
        word_list.add("molecule");
        word_list.add("select");
        word_list.add("wrong");
        word_list.add("gray");
        word_list.add("repeat");
        word_list.add("require");
        word_list.add("broad");
        word_list.add("prepare");
        word_list.add("salt");
        word_list.add("nose");
        word_list.add("plural");
        word_list.add("anger");
        word_list.add("claim");
        word_list.add("continent");
        word_list.add("oxygen");
        word_list.add("sugar");
        word_list.add("death");
        word_list.add("pretty");
        word_list.add("skill");
        word_list.add("women");
        word_list.add("season");
        word_list.add("solution");
        word_list.add("magnet");
        word_list.add("silver");
        word_list.add("thank");
        word_list.add("branch");
        word_list.add("match");
        word_list.add("suffix");
        word_list.add("especially");
        word_list.add("fig");
        word_list.add("afraid");
        word_list.add("huge");
        word_list.add("sister");
        word_list.add("steel");
        word_list.add("discuss");
        word_list.add("forward");
        word_list.add("similar");
        word_list.add("guide");
        word_list.add("experience");
        word_list.add("score");
        word_list.add("apple");
        word_list.add("bought");
        word_list.add("led");
        word_list.add("pitch");
        word_list.add("coat");
        word_list.add("mass");
        word_list.add("card");
        word_list.add("band");
        word_list.add("rope");
        word_list.add("slip");
        word_list.add("win");
        word_list.add("dream");
        word_list.add("evening");
        word_list.add("condition");
        word_list.add("feed");
        word_list.add("tool");
        word_list.add("total");
        word_list.add("basic");
        word_list.add("smell");
        word_list.add("valley");
        word_list.add("nor");
        word_list.add("double");
        word_list.add("seat");
        word_list.add("arrive");
        word_list.add("master");
        word_list.add("track");
        word_list.add("parent");
        word_list.add("shore");
        word_list.add("division");
        word_list.add("sheet");
        word_list.add("substance");
        word_list.add("favor");
        word_list.add("connect");
        word_list.add("post");
        word_list.add("spend");
        word_list.add("chord");
        word_list.add("fat");
        word_list.add("glad");
        word_list.add("original");
        word_list.add("share");
        word_list.add("station");
        word_list.add("dad");
        word_list.add("bread");
        word_list.add("charge");
        word_list.add("proper");
        word_list.add("bar");
        word_list.add("offer");
        word_list.add("segment");
        word_list.add("slave");
        word_list.add("duck");
        word_list.add("instant");
        word_list.add("market");
        word_list.add("degree");
        word_list.add("populate");
        word_list.add("chick");
        word_list.add("dear");
        word_list.add("enemy");
        word_list.add("reply");
        word_list.add("drink");
        word_list.add("occur");
        word_list.add("support");
        word_list.add("speech");
        word_list.add("nature");
        word_list.add("range");
        word_list.add("steam");
        word_list.add("motion");
        word_list.add("path");
        word_list.add("liquid");
        word_list.add("log");
        word_list.add("meant");
        word_list.add("quotient");
        word_list.add("teeth");
        word_list.add("shell");
        word_list.add("neck");
    }

    public String getRandomWord() {
        try {
            int random = new Random().nextInt(word_list.size());
            return word_list.get(random);
        } catch (IndexOutOfBoundsException e) {
            return "kitty";
        }
    }

    public String scrambleWord(String str) {
        char arr[] = str.toCharArray();
        for (int x = 0; x < arr.length; x++) {
            int random1 = new Random().nextInt(arr.length);
            int random2 = new Random().nextInt(arr.length);
            arr = swap(arr, random1, random2);
        }

        String all = swapWholeString(StringUtils.charToStr(arr));

        if (str.equals(all) && str.length() > 1) {
            // not scrambled
            all = swapWholeString(str);
        }

        return all.trim();
    }

    private String swapWholeString(String str) {
        char[] arr = str.toCharArray();
        // if string hasn't scrambled after the scrambleWord method

        for (int x = 0; x < arr.length - 1; x++) {
            arr = swap(arr, x, x + 1);
        }

        return StringUtils.charToStr(arr);
    }

    private char[] swap(char[] arr, int a, int b) {
        char t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;

        return arr;
    }

    public static String searchOnlineWord(String word) {
        String site = "https://www.merriam-webster.com/dictionary/" + word.toLowerCase();
        try {
            WebCrawler crawler = new WebCrawler(site);
            String html = crawler.getHtml();
            return getDefinitionFromHtml(html);
        } catch (Exception e) {
            //e.printStackTrace();
            return "Error";
        }
    }

    private static String getDefinitionFromHtml(String html) {
        String beforeDef = "actual definitions-->";
        html = html.substring(html.indexOf(beforeDef) + beforeDef.length() + 1);

        int index = html.indexOf("first-known-use-explore");
        html = html.substring(0, index);
        html = html.substring(0, html.lastIndexOf("<"));
        //html = html.replaceAll("<p class=\"definition-inner-item with-sense\"><em class=\"sense\">", " enter_here ");
        html = html.replaceAll("<p class=\"definition-inner-item with-sense\"><em class=\"sense\">", "\n\n").trim();

        String arr[] = html.split("\n\n");

        int numDefs = 0;
        for (int x = 0; x < arr.length; x++) {
            numDefs++;

            if (Jsoup.parse(arr[x]).text().isEmpty() || arr[x].contains("<span><em class=\"sub sense alp\">")) {
                arr[x] = "";
                numDefs--;
            }

            arr[x] = arr[x].replaceAll("<span class=\"intro-colon\">:</span>", "");


            if (StringUtils.startWithNumber(arr[x])) {
                arr[x] = arr[x].substring(arr[x].indexOf("<"));
            }
            arr[x] = arr[x].replaceAll("<em class=\"vi\">", "enter_hereenter_here• ");
            arr[x] = Jsoup.parse(arr[x]).text();
            arr[x] = arr[x].replaceAll("enter_here", "\n").trim();
        }

        String[] arr2 = new String[numDefs];

        int current = 0;
        for (int x = 0; x < arr.length; x++) {
            if (!arr[x].trim().isEmpty()) {
                arr2[current++] = arr[x];
            }
        }

        return arr2[new Random().nextInt(arr2.length)].replaceAll("\\u00a0", "");
    }

}
