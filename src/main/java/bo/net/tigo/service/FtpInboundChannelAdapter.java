package bo.net.tigo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aralco on 11/12/14.
 */
public class FtpInboundChannelAdapter {

    private static final Logger logger = LoggerFactory.getLogger(FtpInboundChannelAdapter.class);

//
//    @Test
//    public void runDemo() throws Exception{
//        ConfigurableApplicationContext ctx =
//                new ClassPathXmlApplicationContext("META-INF/spring/integration/FtpInboundChannelAdapterSample-context.xml");
//
//        PollableChannel ftpChannel = ctx.getBean("ftpChannel", PollableChannel.class);
//
//        Message<?> message1 = ftpChannel.receive(2000);
//        Message<?> message2 = ftpChannel.receive(2000);
//        Message<?> message3 = ftpChannel.receive(1000);
//
//        LOGGER.info(String.format("Received first file message: %s.", message1));
//        LOGGER.info(String.format("Received second file message: %s.", message2));
//        LOGGER.info(String.format("Received nothing else: %s.", message3));
//
//        assertNotNull(message1);
//        assertNotNull(message2);
//        assertNull("Was NOT expecting a third message.", message3);
//
//        ctx.close();
//    }

}