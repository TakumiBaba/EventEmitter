package com.takumibaba.eventemitter

object App {
  def main(args: Array[String]) {
    print("Hello com.takumibaba.EventEmitter!")
  }
  var hoge:Hoge = new Hoge()
  hoge.on("hoge", Map(), (resultTuple:List[Any])=>{
    println("on!")
  });
  hoge.emit("hoge", List())
}

class Hoge extends EventEmitter{

}