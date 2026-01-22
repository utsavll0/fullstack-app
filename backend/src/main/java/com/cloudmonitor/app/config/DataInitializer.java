package com.cloudmonitor.app.config;

import com.cloudmonitor.app.model.CloudResource;
import com.cloudmonitor.app.model.User;
import com.cloudmonitor.app.repository.CloudResourceRepository;
import com.cloudmonitor.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudResourceRepository cloudResourceRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize users
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            userRepository.save(user);

            System.out.println("Sample users created:");
            System.out.println("Username: admin, Password: admin123");
            System.out.println("Username: user, Password: user123");
        }

        // Initialize cloud resources
        if (cloudResourceRepository.count() == 0) {
            CloudResource ec2_1 = new CloudResource(
                "web-server-01",
                "EC2 Instance",
                "Running",
                "us-east-1",
                45.2,
                68.5,
                52.3
            );
            cloudResourceRepository.save(ec2_1);

            CloudResource ec2_2 = new CloudResource(
                "app-server-01",
                "EC2 Instance",
                "Running",
                "us-west-2",
                78.9,
                85.2,
                64.1
            );
            cloudResourceRepository.save(ec2_2);

            CloudResource rds_1 = new CloudResource(
                "prod-database",
                "RDS Database",
                "Running",
                "us-east-1",
                32.1,
                55.7,
                71.8
            );
            cloudResourceRepository.save(rds_1);

            CloudResource s3_1 = new CloudResource(
                "backup-bucket",
                "S3 Bucket",
                "Active",
                "us-east-1",
                0.0,
                0.0,
                82.5
            );
            cloudResourceRepository.save(s3_1);

            CloudResource lambda_1 = new CloudResource(
                "data-processor",
                "Lambda Function",
                "Running",
                "us-west-2",
                15.5,
                32.1,
                0.0
            );
            cloudResourceRepository.save(lambda_1);

            CloudResource ec2_3 = new CloudResource(
                "api-server-01",
                "EC2 Instance",
                "Stopped",
                "eu-west-1",
                0.0,
                0.0,
                45.2
            );
            cloudResourceRepository.save(ec2_3);

            CloudResource elb_1 = new CloudResource(
                "prod-load-balancer",
                "Elastic Load Balancer",
                "Running",
                "us-east-1",
                22.3,
                18.7,
                0.0
            );
            cloudResourceRepository.save(elb_1);

            CloudResource dynamodb_1 = new CloudResource(
                "session-store",
                "DynamoDB Table",
                "Active",
                "ap-southeast-1",
                12.4,
                28.6,
                45.3
            );
            cloudResourceRepository.save(dynamodb_1);

            System.out.println("Sample cloud resources created: " + cloudResourceRepository.count());
        }
    }
}
