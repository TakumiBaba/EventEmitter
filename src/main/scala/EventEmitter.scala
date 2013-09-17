package com.takumibaba.eventemitter
/**
 * Created with IntelliJ IDEA.
 * User: takumi
 * Date: 2013/09/11
 * Time: 16:03
 * To change this template use File | Settings | File Templates.
 */
trait EventEmitter {
  var events:List[Any] = List();

  def add_listener(typ:String, params:Map[String, Any], callback:(List[Any])=>Any){
    var id:Int = 0;
    if(events.isEmpty){
      id = 0;
    }else{
      var lastEvent:Map[String, Any] = events.last.asInstanceOf[Map[String, Any]]
      id = lastEvent.get("id").get.asInstanceOf[Int]+1
    }
    events = events ::: List(Map(
      "type" -> typ,
      "listener" -> callback,
      "params" -> params,
      "id" -> id
    ))
  }
  def on(typ:String, params:Map[String, Any], callback:(List[Any])=>Any){
    add_listener(typ, params, callback);
  }

  def remove_listener(id_or_type:Any) = {
    id_or_type match {
      case i:Int => {
        for(event <- events){
          var e:Map[String, Any] = event.asInstanceOf[Map[String, Any]];
          if(e.get("id").get == i){
            events = events.filterNot(_ == event)
          }
        }
      }
      case s:String => {
        for(event <- events){
          var e:Map[String, Any] = event.asInstanceOf[Map[String, Any]];
          if(e.get("type").get == s){
            events = events.filterNot(_ == event)
          }
        }
      }

    }
  }
  def emit(typ:String, data:List[Any]) = {
    for(event <- events){
      var e:Map[String, Any] = event.asInstanceOf[Map[String, Any]];
      val eventType:String = e.get("type").get.toString
      if(eventType == typ){
        var listener:(Any)=>Any = e.get("listener").get.asInstanceOf[(Any)=>Any];
        var params:Map[String, Any] = e.getOrElse("params", Map()).asInstanceOf[Map[String, Any]]
        if(params.get("once").getOrElse(false).asInstanceOf[Boolean]){
          events = events.filterNot(_ == event)
        }
//        listener(List(params))
        listener(data)
      }else if(eventType.matches("_")){
        var listener:(Any)=>Any = e.get("listener").get.asInstanceOf[(Any)=>Any];
        var params:Map[String, Any] = e.getOrElse("params", Map()).asInstanceOf[Map[String, Any]]
        if(params.get("once").getOrElse(false).asInstanceOf[Boolean]){
          events = events.filterNot(_ == event)
        }
//        listener(List(typ, params))
        listener(data)
      }

    }
//    for(event <- events){
//      var e:Map[String, Any] = event.asInstanceOf[Map[String, Any]];
//      if(e.get(typ).isEmpty){
//        remove_listener(e.get("id").get)
//      }
//    }
  }
  def once(typ:String, callback:(List[Any])=>Any) = {
    add_listener(typ, Map("once" -> true), callback);
  }
}
