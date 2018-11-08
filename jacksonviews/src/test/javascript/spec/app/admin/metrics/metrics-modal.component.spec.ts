import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { JacksonviewsTestModule } from '../../../test.module';
import { JacMetricsMonitoringModalComponent } from 'app/admin/metrics/metrics-modal.component';
import { JacMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
    describe('JacMetricsMonitoringModalComponent', () => {
        let comp: JacMetricsMonitoringModalComponent;
        let fixture: ComponentFixture<JacMetricsMonitoringModalComponent>;
        let service: JacMetricsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JacksonviewsTestModule],
                declarations: [JacMetricsMonitoringModalComponent]
            })
                .overrideTemplate(JacMetricsMonitoringModalComponent, '')
                .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JacMetricsMonitoringModalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JacMetricsService);
        });

        describe('ngOnInit', () => {
            it('should count the numbers of each thread type', () => {
                comp.threadDump = [
                    { name: 'test1', threadState: 'RUNNABLE' },
                    { name: 'test2', threadState: 'WAITING' },
                    { name: 'test3', threadState: 'TIMED_WAITING' },
                    { name: 'test4', threadState: 'BLOCKED' },
                    { name: 'test5', threadState: 'BLOCKED' },
                    { name: 'test5', threadState: 'NONE' }
                ];
                fixture.detectChanges();

                expect(comp.threadDumpRunnable).toBe(1);
                expect(comp.threadDumpWaiting).toBe(1);
                expect(comp.threadDumpTimedWaiting).toBe(1);
                expect(comp.threadDumpBlocked).toBe(2);
                expect(comp.threadDumpAll).toBe(5);
            });

            it('should return badge-info for WAITING', () => {
                expect(comp.getBadgeClass('WAITING')).toBe('badge-info');
            });

            it('should return badge-warning for TIMED_WAITING', () => {
                expect(comp.getBadgeClass('TIMED_WAITING')).toBe('badge-warning');
            });

            it('should return badge-danger for BLOCKED', () => {
                expect(comp.getBadgeClass('BLOCKED')).toBe('badge-danger');
            });

            it('should return undefined for anything else', () => {
                expect(comp.getBadgeClass('')).toBe(undefined);
            });
        });

        describe('getBadgeClass', () => {
            it('should return badge-success for RUNNABLE', () => {
                expect(comp.getBadgeClass('RUNNABLE')).toBe('badge-success');
            });

            it('should return badge-info for WAITING', () => {
                expect(comp.getBadgeClass('WAITING')).toBe('badge-info');
            });

            it('should return badge-warning for TIMED_WAITING', () => {
                expect(comp.getBadgeClass('TIMED_WAITING')).toBe('badge-warning');
            });

            it('should return badge-danger for BLOCKED', () => {
                expect(comp.getBadgeClass('BLOCKED')).toBe('badge-danger');
            });

            it('should return undefined for anything else', () => {
                expect(comp.getBadgeClass('')).toBe(undefined);
            });
        });
    });
});
