import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { NoautocompleteTestModule } from '../../../test.module';
import { NoaMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { NoaMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
    describe('NoaMetricsMonitoringComponent', () => {
        let comp: NoaMetricsMonitoringComponent;
        let fixture: ComponentFixture<NoaMetricsMonitoringComponent>;
        let service: NoaMetricsService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [NoautocompleteTestModule],
                    declarations: [NoaMetricsMonitoringComponent]
                })
                    .overrideTemplate(NoaMetricsMonitoringComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(NoaMetricsMonitoringComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoaMetricsService);
        });

        describe('refresh', () => {
            it('should call refresh on init', () => {
                // GIVEN
                const response = {
                    timers: {
                        service: 'test',
                        unrelatedKey: 'test'
                    },
                    gauges: {
                        'jcache.statistics': {
                            value: 2
                        },
                        unrelatedKey: 'test'
                    }
                };
                spyOn(service, 'getMetrics').and.returnValue(of(response));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.getMetrics).toHaveBeenCalled();
                expect(comp.servicesStats).toEqual({ service: 'test' });
                expect(comp.cachesStats).toEqual({ jcache: { name: 17, value: 2 } });
            });
        });

        describe('isNan', () => {
            it('should return if a variable is NaN', () => {
                expect(comp.filterNaN(1)).toBe(1);
                expect(comp.filterNaN('test')).toBe(0);
            });
        });
    });
});
