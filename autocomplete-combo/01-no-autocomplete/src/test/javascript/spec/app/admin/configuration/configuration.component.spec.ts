import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NoautocompleteTestModule } from '../../../test.module';
import { NoaConfigurationComponent } from 'app/admin/configuration/configuration.component';
import { NoaConfigurationService } from 'app/admin/configuration/configuration.service';
import { ITEMS_PER_PAGE } from 'app/shared';
import { Log } from 'app/admin';

describe('Component Tests', () => {
    describe('NoaConfigurationComponent', () => {
        let comp: NoaConfigurationComponent;
        let fixture: ComponentFixture<NoaConfigurationComponent>;
        let service: NoaConfigurationService;

        beforeEach(
            async(() => {
                TestBed.configureTestingModule({
                    imports: [NoautocompleteTestModule],
                    declarations: [NoaConfigurationComponent],
                    providers: [NoaConfigurationService]
                })
                    .overrideTemplate(NoaConfigurationComponent, '')
                    .compileComponents();
            })
        );

        beforeEach(() => {
            fixture = TestBed.createComponent(NoaConfigurationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoaConfigurationService);
        });

        describe('OnInit', () => {
            it('should set all default values correctly', () => {
                expect(comp.configKeys).toEqual([]);
                expect(comp.filter).toBe('');
                expect(comp.orderProp).toBe('prefix');
                expect(comp.reverse).toBe(false);
            });
            it('Should call load all on init', () => {
                // GIVEN
                const body = [{ config: 'test', properties: 'test' }, { config: 'test2' }];
                const envConfig = { envConfig: 'test' };
                spyOn(service, 'get').and.returnValue(of(body));
                spyOn(service, 'getEnv').and.returnValue(of(envConfig));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.get).toHaveBeenCalled();
                expect(service.getEnv).toHaveBeenCalled();
                expect(comp.configKeys).toEqual([['0', '1', '2', '3']]);
                expect(comp.allConfiguration).toEqual(envConfig);
            });
        });
        describe('keys method', () => {
            it('should return the keys of an Object', () => {
                // GIVEN
                const data = {
                    key1: 'test',
                    key2: 'test2'
                };

                // THEN
                expect(comp.keys(data)).toEqual(['key1', 'key2']);
                expect(comp.keys(undefined)).toEqual([]);
            });
        });
    });
});
