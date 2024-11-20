package com.daon.onjung.core.config;

import lombok.Setter;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class SchedulerConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(AutowiringSpringBeanJobFactory jobFactory) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(jobFactory); // Spring 빈으로 관리되는 JobFactory 주입
        return factoryBean;
    }

    @Bean
    public AutowiringSpringBeanJobFactory jobFactory(AutowireCapableBeanFactory beanFactory) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setBeanFactory(beanFactory); // Spring의 BeanFactory 주입
        return jobFactory;
    }

    @Setter
    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {
        private AutowireCapableBeanFactory beanFactory;

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job); // Spring 의존성 주입
            return job;
        }
    }
}
