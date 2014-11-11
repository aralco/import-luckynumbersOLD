package bo.net.tigo.service;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by aralco on 11/11/14.
 */
@Service
public class GetFrozenAndFreeNumbers implements Runnable {

    @Override
    public void run() {
        System.out.println("Tarea programada: "+new Date());
    }
}
