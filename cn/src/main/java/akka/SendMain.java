package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Date;

public class SendMain {
    public static void main(String[] args) throws InterruptedException {
        ActorSystem actorSystem  = ActorSystem.create();
        ActorRef actorRef = actorSystem.actorOf(Props.create(SendActor.class),"sendActor");
        actorRef.tell(new Date(),actorRef);
        Thread.sleep(3000);
        actorSystem.terminate();
    }
}
