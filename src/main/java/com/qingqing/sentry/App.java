package com.qingqing.sentry;
import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;

public class App {
        public static void main(String... args) {
                Sentry.init("http://f9005f8e125d4cc0b66663532834fa9a:f2e808d22a184820873153e930644d90@172.20.13.154:9000/3");
                logSimpleMessage();
                logException();
                logException1();
                Sentry.close();
        }

       static void unsafeMethod() {
                throw new UnsupportedOperationException("You shouldn't call this!");
        }
        static void illegalMethod() {
            throw new IllegalArgumentException("test for sentry");
        }

       static void logSimpleMessage() {
                // This sends an event to Sentry.
                EventBuilder eventBuilder = new EventBuilder()
                        .withMessage("This is a test")
                        .withLevel(Event.Level.INFO)
                        .withLogger(App.class.getName());

                // Note that the *unbuilt* EventBuilder instance is passed in so that
                // EventBuilderHelpers are run to add extra information to your event.
                Sentry.capture(eventBuilder);
        }

       static void logException() {
                try {
                        unsafeMethod();
                } catch (Exception e) {
                        // This sends an exception event to Sentry.
                        EventBuilder eventBuilder = new EventBuilder()
                                .withMessage("Exception caught on line42")
                                .withLevel(Event.Level.ERROR)
                                .withLogger(App.class.getName())
                                .withRelease("")
                                //.withTag()
                                .withFingerprint("ExceptionTest")
                                .withSentryInterface(new ExceptionInterface(e));

                        // Note that the *unbuilt* EventBuilder instance is passed in so that
                        // EventBuilderHelpers are run to add extra information to your event.
                        Sentry.capture(eventBuilder);
                }
        }
    static void logException1() {
        try {
            illegalMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry.
            EventBuilder eventBuilder = new EventBuilder()
                    .withMessage("Exception caught on line56")
                    .withLevel(Event.Level.ERROR)
                    .withFingerprint("ExceptionTest")
                    .withLogger(App.class.getName())
                    .withSentryInterface(new ExceptionInterface(e));

            // Note that the *unbuilt* EventBuilder instance is passed in so that
            // EventBuilderHelpers are run to add extra information to your event.
            Sentry.capture(eventBuilder);
        }
    }
}