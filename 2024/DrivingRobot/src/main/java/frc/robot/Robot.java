// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private CANSparkMax m_leftMotor;
  private CANSparkMax m_leftFollower;
  private CANSparkMax m_rightMotor;
  private CANSparkMax m_rightFollower;
  private CANSparkMax m_Shooter;
  private CANSparkMax m_Shooter_Follower;

  private DifferentialDrive m_robotDrive; 
  private DifferentialDrive another_m_robotDrive; 
  private XboxController m_driverController;

  public Robot() 
  {

  }

  @Override
  public void robotInit() {

    m_driverController =  new XboxController(0);
    m_rightMotor = new CANSparkMax(2, MotorType.kBrushed);
    m_rightFollower = new CANSparkMax(1, MotorType.kBrushed);
    m_leftMotor = new CANSparkMax(3, MotorType.kBrushed);
    m_leftFollower = new CANSparkMax(4, MotorType.kBrushed);

    m_robotDrive = new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
    another_m_robotDrive = new DifferentialDrive(m_leftFollower::set, m_rightFollower::set);

    m_Shooter = new CANSparkMax(5, MotorType.kBrushed);
    m_Shooter_Follower = new CANSparkMax(6, MotorType.kBrushed);

    m_rightMotor.restoreFactoryDefaults();
    m_rightFollower.restoreFactoryDefaults();
    m_leftMotor.restoreFactoryDefaults();
    m_leftFollower.restoreFactoryDefaults();

    m_Shooter.restoreFactoryDefaults();
    m_Shooter_Follower.restoreFactoryDefaults();
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);
    SendableRegistry.addChild(m_robotDrive, m_leftMotor);

    // m_rightFollower.follow(m_rightMotor);
    // m_leftFollower.follow(m_leftMotor);

    m_Shooter_Follower.follow(m_Shooter);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
    m_rightFollower.setInverted(true);

    m_leftMotor.setInverted(false);
    m_leftFollower.setInverted(false);

  }

  @Override
  public void teleopPeriodic() {
    // Drive with split arcade drive.
    // That means that the Y axis of the left stick moves forward
    // and backward, and the X of the right stick turns left and right.
    m_robotDrive.arcadeDrive(m_driverController.getLeftY(), m_driverController.getRightX());
    another_m_robotDrive.arcadeDrive(m_driverController.getLeftY(), m_driverController.getRightX());

    if( m_driverController.getAButton() )
    {
      m_Shooter.set(0.5);
    }
    else if( m_driverController.getYButton() )
    {
      m_Shooter.set(-1
      
      );
    }
    else
    {
      m_Shooter.set(0.0);
    }
  }
}
