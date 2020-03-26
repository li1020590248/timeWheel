package akka;

import akka.actor.AbstractActor;

public class SendActor extends AbstractActor{
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, msg ->{
            System.out.println("----------i reveiver msm" +msg);
        }).matchAny(msg ->{
            System.out.println("----------who are you--------");
        }).build();
    }
}
